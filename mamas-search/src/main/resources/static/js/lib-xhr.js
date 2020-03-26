XHR = function (url, method, header, body) {
    this.url = null;
    this.method = null;
    this.body = {};
    this.header = {};

    this.setUrl(url).setMethod(method).setHeader(header).setBody(body);
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


XHR.prototype.request = function (callbackWhenSuccess, callbackWhenError) {
    var xhr = new XMLHttpRequest();

    if (this.method === 'GET') {
        this.setUrlWithQueryString();
        this.body = null;
    } else {
        this.body = JSON.stringify(this.body);
        if (!this.header['Content-Type'])
            this.header['Content-Type'] = 'application/json';
    }

    // onreadystatuschange라는 리스너도 있는데 xhr객체는 요청 과정에 따라 4가지 상태를 가지고 4번째가 DONE이다.
    // onload는 DONE일때의 리스너다. 순서대로 하자면 UNSET, OPENED, HEADERS_RECEIVED, DONE이다.
    xhr.onload = function () {
        var response = xhr.responseText;
        if (xhr.status === 200 || xhr.status === 201) {
            if (callbackWhenSuccess)
                callbackWhenSuccess(response);
        } else {
            if (callbackWhenError)
                callbackWhenError(response)
        }
    };

    xhr.open(this.method, this.url);

    for (var key in this.header) {
        xhr.setRequestHeader(key, this.header[key]);
    }
    xhr.send(this.body);
};