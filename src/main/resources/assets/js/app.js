/**
 * Created by adel on 9/16/17.
 */

String.prototype.replaceAll = function (search, replace) {
    return this.split(search).join(replace);
};

function escape(s) {
    return s.replaceAll('&quot;', '\"')
        .replaceAll('&#39;', '\'');
}


var QuestionForm = React.createClass({
    getInitialState: function () {
        return {
            page: 0
        };
    },
    render: function () {
        return (
            <div className="col-md-4">
                <div className="container">
                    <form className="affix">
                        <div className="form-group">
                            <label htmlFor="questionInput">Search questions</label>
                            <input type="text" className="form-control" id="questionInput"
                                   aria-describedby="questionSearchHelp"
                                   ref="intitle"
                                   placeholder="Title"/>
                            <small id="questionSearchHelp" className="form-text text-muted">
                                Enter part of title
                            </small>
                            <label htmlFor="questionSort">Sort by</label>
                            <select defaultValue="activity" className="form-control" id="questionSort" ref="sort">
                                <option value="activity">Activity</option>
                                <option value="votes">Votes</option>
                                <option value="creation">Creation</option>
                                <option value="relevation">Relevation</option>
                            </select>
                        </div>
                        <button onClick={this.load} type="button" className="btn btn-primary">Search</button>
                    </form>
                </div>
            </div>
        );
    },
    search: function (cb) {
        var intitle = ReactDOM.findDOMNode(this.refs.intitle).value,
            sort=ReactDOM.findDOMNode(this.refs.sort).value;

        axios.get(`/api/questions/search?intitle=${intitle}&page=${this.state.page}&sort=${sort}`)
            .then(res => {
                const questions = res.data.questions;
                cb(questions, res.data.hasMore);
            });
    },
    load: function(e) {
        if (e)
            e.preventDefault();
        this.search(this.props.loadedCb);
    },
    loadMore: function () {
        this.setState({page: ++this.state.page});
        this.search(this.props.loadedMoreCb);
    }
});

var QuestionRow = React.createClass({
    render: function () {
        var title = this.props.data.title,
            url = this.props.data.url,
            author = this.props.data.author,
            date = new Date(this.props.data.date * 1000).toLocaleDateString('en-US',
                {weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'});

        return (
            <tr>
                <td><a href={url}>{escape(title)}</a></td>
                <td>{date}</td>
                <td><a href={author.url}>{author.name}</a></td>
            </tr>
        );
    }
});

var QuestionTable = React.createClass({
    render: function () {

        var data = this.props.data;
        if (data.length > 0) {
            var questionTemplate = data.map(function (item, index) {
                return (
                    <QuestionRow key={item.id} data={item}/>
                );
            });
            var questionTableTemplate =
                <div>
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Title</th>
                            <th>Date</th>
                            <th>Author</th>
                        </tr>
                        </thead>
                        <tbody>
                        {questionTemplate}
                        </tbody>
                    </table>
                    <a className={'btn-link ' + (this.props.hasMore ? '' : 'hidden')} href="#" onClick={this.loadMore}>Load more</a>
                </div>;
        }
        else {
            var questionTableTemplate = <p>No questions found. Use search form</p>;
        }

        return (
            <div className="col-md-8">{questionTableTemplate}</div>
        );
    },
    loadMore: function (e) {
        e.preventDefault();
        this.props.loadMore();
    }
});

function idEqualsPred(item) {
    return function(another) {
        return item.id == another.id;
    };
}

Array.prototype.filterUniques = function (eqPred) {
    return this.filter(function (item, index, array) {
        return array.findIndex(eqPred(item)) == index;
    });
};

var QuestionApp = React.createClass({
    getInitialState: function () {
        return {
            questions: [],
            hasMore: false,
            page: 0
        }
    },
    formLoadedCb: function (questions, hasMore) {
        this.setState({questions: questions, hasMore: hasMore});
    },
    formLoadedMoreCb: function (questions, hasMore) {
        this.setState({questions: this.state.questions.concat(questions)
            .filterUniques(idEqualsPred), hasMore: hasMore});
    },
    render: function () {
        return (
            <div className="row">
                <QuestionForm ref={instance => { this.questionForm = instance; }}
                              loadedCb={this.formLoadedCb}
                              loadedMoreCb={this.formLoadedMoreCb}
                />
                <QuestionTable data={this.state.questions} hasMore={this.state.hasMore}
                               loadMore={this.loadMore}
                />
            </div>
        );
    },
    loadMore: function () {
        this.questionForm.loadMore();
    }
});

ReactDOM.render(
    <QuestionApp/>,
    document.getElementById('root')
)
;