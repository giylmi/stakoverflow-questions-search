/**
 * Created by adel on 9/17/17.
 */

String.prototype.replaceAll = function (search, replace) {
    return this.split(search).join(replace);
};

String.prototype.escapeHtml = function () {
    return this.replaceAll('&quot;', '\"')
        .replaceAll('&#39;', '\'');
};

Array.prototype.filterUniques = function (eqPred) {
    return this.filter(function (item, index, array) {
        return array.findIndex(eqPred(item)) == index;
    });
};
