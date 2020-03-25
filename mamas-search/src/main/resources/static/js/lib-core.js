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

// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
    'use strict'

    window.addEventListener('load', function () {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation')

        // Loop over them and prevent submission
        Array.prototype.filter.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
    }, false)
}());
