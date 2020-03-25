XHR = function (url, header, body) {
    this.url = null;
    this.callbackWhenSuccess = null;
    this.callbackWhenError = null;
    this.defaultCallback = null;
    this.method = null;
    this.body = {};
    this.header = {};
};

XHR.prototype.setUrl = function (url) {
    this.url = url;
    return this;
};

XHR.prototype.setBody = function (body) {
    this.body = body;
    return this;
};

XHR.prototype.setHeader = function (header) {
    this.header = header;
    return this;
};

XHR.prototype.setMethod = function (method) {
    this.method = method;
    return this;
};

XHR.prototype.setUrlWithQueryString = function () {
    var queryString = "";

    for (var query in this.body) { // 이 함수
        var queryValue = this.body[query];

        if (queryValue instanceof Array) { // value가 여러개면?
            for (let value in queryValue) {
                if (value !== undefined && value !== null) {
                    value = encodeURIComponent(value);
                    queryString += ('&' + query + '=' + value);
                }
            }
        } else {
            if (queryValue !== undefined && queryValue !== null) {
                queryValue = encodeURIComponent(queryValue);
                queryString += ('&' + query + '=' + queryValue);
            }
        }
    }
    this.url += ('?' + queryString);
};

XHR.prototype.setSuccessCallback = function(callback) {
    this.callbackWhenSuccess = callback;
    return this;
};

XHR.prototype.setErrorCallback = function(callback) {
    this.callbackWhenError = callback;
    return this;
};

XHR.prototype.defaultCallback = function(callback) {
    this.defaultCallback = callback;
    return this;
};

XHR.prototype.request = function () {
    var xhr = new XMLHttpRequest();

    if (this.method === 'GET') {
        this.setUrlWithQueryString();
        this.body = null;
    } else {
        this.body = JSON.stringify(this.body);
        if (!this.header['Content-Type'])
            this.header['Content-Type'] = 'application/json';
    }

    xhr.onload = function(event) {

    };

    xhr.open(this.method, this.url);

    for (var key in this.header) {
        xhr.setRequestHeader(key, this.header[key]);
    }
    xhr.send(this.body);
};