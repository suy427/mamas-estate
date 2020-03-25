class LibCore {
    constructor() {
        this.context = this.setContext()
    }

    setContext() {
        $.getElementsByName('ctx')[0].getAttribute('content');
    }

    getURL(url) {return this.context+url}

    loadData(url, params, callbackWhenSuccess, callbackWhenFail) {
        this.getXHR().request(callbackWhenSuccess)
    }


}


function loadData(url, params, callbackWhenSuccess){
    getXHR(url, params).request(callbackWhenSuccess);
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

function clearClick() {
    //////// test sample view
    var recommendations = document.getElementById('recommendation-result');
    recommendations.innerHTML = '추천어는 여기에..';
    document.getElementById('itemId').value = 'GNM000101'
    document.getElementById('mapType').value = 1
    document.getElementById('nodeSize').value = 5
}