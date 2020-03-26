function loadData(url, params, callbackWhenSuccess){
    var get = new XHR(url, 'GET', params);
    get.request(callbackWhenSuccess);
}

function getRecommends(url, itemId, mapType, nodeSize){
    loadData(url, {type: Number(mapType), id: itemId, size: Number(nodeSize)},
        function(data) {
            var recommendations = document.getElementById('recommendation-result');
            var datas = data
            var result = ''
            for(var i in datas) {
                result += (result ? ", " : "") + datas[i].label
            }
            recommendations.innerHTML = result;
        }
    )
}


URLS = {
    INDEX_PAGE: getURL(''),

    USER_INFO_PAGE: getURL(''),
    ESTATE_INFO_PAGE: getURL(),
    BID_INFO_PAGE: getURL(''),
    CONTRACT_INFO_PAGE: getURL(''),

    USER_SEARCH_RESULT: getURL(''),
    ESTATE_SEARCH_RESULT: getURL(''),
    CONTRACT_SEARCH_RESULT: getURL(''),
    BID_SEARCH_RESULT: getURL(''),
};