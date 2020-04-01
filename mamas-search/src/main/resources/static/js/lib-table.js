/**
 * TABLE 구조
 * <DIV [NAME]-LIST-GROUP>
 *   <DIV LABEL-GROUP>
 *     <LABEL>
 *       <DIV BUTTONS/>
 *     </LABEL>
 *   </DIV LABEL-GROUP>
 *
 *   <DIV TABLE-GROUP>
 *     <DIV HEAD>
 *       <DIV HEAD-INNER>
 *         <TABLE HEADER/>
 *       </DIV HEAD-INNER>
 *     </DIV HEAD> // 여기까지 헤더부분
 *
 *     <BR>
 *
 *     <DIV HEAD> // 없는 헤더
 *       <DIV HEAD-INNER>
 *         <TABLE HEADER/>
 *       </DIV HEAD-INNER>
 *     </DIV HEAD>
 *
 *     <DIV TABLE-BODY> //진짜 테이블 바디
 *       <TABLE BODY/>
 *     </DIV TABLE-BODY>
 *   </DIV TABLE-GROUP>
 * </DIV [NAME]-LIST-GROUP>
 */
function ListGroup(name) {
    this.listGroupDiv = newElement(
        'div',
        {
            "class": name + '-list-group'
        }
    );
}

ListGroup.prototype.getList = function () {
    return this.listGroupDiv;
};

ListGroup.prototype.setLabelDiv = function (labelDiv) {
    this.listGroupDiv.append(labelDiv);
};

ListGroup.prototype.setTable = function (table) {
    this.listGroupDiv.append(table);
};

function LabelDiv(name) {
    this.labelDiv = newElement(
        'div',
        {
            "class": name + '-list-labelDiv'
        }
    );
}

LabelDiv.prototype.setLabel = function (label) {
    this.labelDiv.append(label);
};

LabelDiv.prototype.getLabelDiv = function () {
    return this.labelDiv;
};

Label = function (master, text) {
    this.label = newElement(
        'label',
        {
            "for": master
        },
        text
    );
};

Label.prototype.setButtons = function (buttonDiv) {
    this.label.append(buttonDiv);
};

Label.prototype.getLabel = function () {
    return this.label;
};

Buttons = function (name, buttonInfo) {
    if (buttonInfo.length < 1)
        return;

    var buttonDiv = newElement(
        "div",
        {
            "class": name + "-label-button-div"
        }
    );
    /**
     * buttonInfo = [
     *      button_1: [{
     *          att1: value1,
     *          att2: value2,
     *          ...
     *          },
     *          inner_text
     *      ],
     *      button_2: [{
     *          att1: value1,
     *          att2: value2,
     *          ...
     *          },
     *          inner_text
     *      ],
     *      ...
     *  ]
     */
    for (var buttonName in buttonInfo) {
        var button = newElement(
            "button",
            buttonInfo[buttonName][0],
            buttonInfo[buttonName][1]
        );
        buttonDiv.append(button);
    }
};

Buttons.prototype.getButtons = function () {
    return this.buttonDiv; // TODO 얜 또 왜이래..?ㅠ
};

TableDiv = function (name) {
    this.tableDiv = newElement(
        'div',
        {
            "name": name + '-table-group',
            "class": "mb-3 dataTables_scroll"
        }
    );
};

TableDiv.prototype.setTable = function (tableHead, tableBody) {
    this.tableDiv.append(tableHead);
    this.tableDiv.append('<br>');
    this.tableDiv.append(tableBody);
};

TableDiv.prototype.getTableDiv = function () {
    return this.tableDiv;
};

TableHeadDiv = function (outerAttributes, innerAttributes) {
    this.tableHeadDiv = newElement(
        'div',
        outerAttributes
    );
    this.tableHeadInnerDiv = newElement(
        'div',
        innerAttributes
    );
    this.tableHeadDiv.append(this.tableHeadInnerDiv);
};

TableHeadDiv.prototype.setTableHead = function(tableHead) {
    this.tableHeadInnerDiv.append(tableHead);
};

TableHead = function (columns) {
    this.attributes = {
        "class": "table table-striped table-bordered table-sm dataTable",
        "role": "grid",
        "style": "margin-left: 0;" // TODO 이건 Style이야~
    };
    this.columns = columns;
    this.tableHead = newElement(
        'table',
        this.attributes,
    );
    this.thead = newElement('thead');
    this.addThead(this.attributes);
    this.tableHead.append(this.thead);
};

TableHead.prototype.addThead = function () {
    var columns = this.columns;
    var thead = this.thead;
    var attributes = {
        "class": "th-sm sorting table-head-header",
        "tabindex": "0",
        "rowspan": "1",
        "colspan": "1"
    };
    var tr = document.createElement('tr');
    tr.setAttribute('role', 'row');

    for (var column in columns) {
        var th = document.createElement('th');
        for (var name in this.attributes) {
            th.setAttribute(name, attributes[name]);
        }
        th.innerText = column;
        tr.append(th);
    }
    thead.append(tr);
};

TableBody = function (columns) {
    this.attributes = {};
    this.tbody = newElement('tbody');
    this.addTbody(this.attributes);
};


TableBody.prototype.addTbody = function (attributes) {
    var tbody = this.tbody;
};

ListGroup.prototype.setTableHead = function (headerInfo) {
    var tableHeadDiv = newElement(
        'div',
        {
            "name": this.tableName + '-header-head',
            "class": "dataTables_scrollHead"
        });

    var tableHeadInnerDiv = newElement(
        'div',
        {
            "name": this.tableName + '-header-head-inner',
            "class": "dataTables_scrollHeadInner",
            "style": "box-sizing: content-box;"
        }
    );

    var table = newElement(
        'table',
        {
            "class": "table table-striped table-bordered table-sm dataTable",
            "role": "grid",
            "style": "margin-left: 0;"
        }
    );

    var thead = this.addThead();
    var tbody = this.addTbody(bodyAttributes);

    table.append(thead);
    table.append(tbody);

    tableHeadInnerDiv.append(table);
    tableHeadDiv.append(tableHeadInnerDiv);
    this.tableDiv.append(tableHeadDiv);

    return this;
};

ListGroup.prototype.setTableBody = function () {

};


ListGroup.prototype.addColumns = function (row, columns) {
    if (columns) {
        for (var column in columns) {
            var td = document.createElement('td');
            td.innerText = column;
            row.append(td);
        }
    } else {
        throw 'no column info to make!'
    }
};

ListGroup.prototype.addRows = function (table, rows) {
    if (rows) {
        for (var row in rows) {
            var tr = document.createElement('tr');
            this.addColumns(tr, row);

            table.append(tr);
        }
    } else {
        throw 'no row info to make!'
    }
};