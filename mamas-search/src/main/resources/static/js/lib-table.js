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

function newElement(type, attributes, inner) {
    var element = document.createElement(type);

    for (var attributeName in attributes) {
        element.setAttribute(attributeName, attributes[attributeName])
    }
    if (inner) {
        element.innerHTML = inner;
    }
    return element;
}


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
            "for": master + '-table'
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
Buttons = function (name, buttonInfo) {
    if (buttonInfo.length < 1)
        return;

    var buttonDiv = newElement(
        "div",
        {
            "class": name + "-label-button-div"
        }
    );

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

TableDiv.prototype.setTable = function (tableHeadDiv, tableBodyDiv) {
    this.tableDiv.append(tableHeadDiv);
    this.tableDiv.append('<br>');
    this.tableDiv.append(tableBodyDiv);
};

TableDiv.prototype.getTableDiv = function () {
    return this.tableDiv;
};

TableHeadDiv = function () {
    this.tableHeadDiv = newElement(
        'div',
        {"class": "dataTables_scrollHead"}
    );
    this.tableHeadInnerDiv = newElement(
        'div',
        {
            "class": "dataTables_scrollHeadInner",
            "style": "box-sizing: content-box;"
        }
    );
};

TableHeadDiv.prototype.setHeadTable = function (headTable) {
    this.tableHeadInnerDiv.append(headTable);
};

TableHeadDiv.prototype.getTableHeadDiv = function () {
    return this.tableHeadDiv;
};

HeadTable = function (columns) {
    this.attributes = { // 얘는 table 태그의 attributes
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
    this.addThead(); // <thead> 태그 붙이는거
    this.tableHead.append(this.thead);
};

HeadTable.prototype.addThead = function () {
    var columns = this.columns;
    var thead = this.thead;
    var attributes = { // 얘는 th들의 attributes
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

TableBodyDiv = function () {
    this.tableBodyDiv = newElement(
        'div',
        {"class": "dataTables_scrollBody"}
    );
};

TableBodyDiv.prototype.setBodyTable = function (bodyTable) {
    this.tableBodyDiv.append(bodyTable);
};

TableBodyDiv.prototype.getTableBodyDiv = function () {
    return this.tableBodyDiv;
};

BodyTable = function (columns) {
    this.columns = columns;
    this.rowNum = 0;
    this.attributes = {
        "id": "owningestate-table",
        "class": "table table-hover table-bordered table-sm"
    };
    this.table = newElement(
        'table',
        this.attributes
    );
    this.thead = newElement('thead');
    this.addThead();

    this.tbody = newElement('tbody');
    this.addTbody();

    this.table.append(this.thead);
    this.table.append(this.tbody);
};

BodyTable.prototype.addThead = function () {
    var columns = this.columns;
    var attributes = {"class": "th-sm sorting table-body-header"};
    var tr = newElement('tr');
    for (var column in columns) {
        var th = newElement('th', attributes, column);
        tr.append(th);
    }
    this.thead.append(tr);
};

/// todo 여기서부터 하면됨.
BodyTable.prototype.addTbody = function (rows) {
    var tbody = this.tbody;

    for (var row in rows) {
        tbody.append(this.addRow(row));
    }
    this.table.append(tbody);
};

BodyTable.prototype.addRow = function (row) {
    var tr = newElement(
        'tr',
        {"tabindex": "0"}
    );
    var th = newElement(
        'th',
        {"scope": "row"},
        ++this.rowNum
    );
    tr.append(th);

    for (var column in row) {
        var td = newElement(
            'td',
            {},
            column
        );
        tr.append(td);
    }

    return tr;
};

var arr1 = [
    ["Garrett", "Accountant", "Tokyo", "63", "2011", "$170"],
    ["Ashton", "Junior", "San", "66", "2009", "$86"],
    ["Ashton", "Junior", "San", "66", "2009", "$86"],
    ["Ashton", "Junior", "San", "66", "2009", "$86"],
    ["Garrett", "Accountant", "Tokyo", "63", "2011", "$170"],
    ["Garrett", "Accountant", "Tokyo", "63", "2011", "$170"],
    ["Garrett", "Accountant", "Tokyo", "63", "2011", "$170"],
];

function ListGroupMaker(name, columns, rows, labelText, buttonInfo) {
    /* 전체 : label넣고, table넣고 */
    this.listGroup = new ListGroup(name);

    /* 라벨그룳 */
    this.labelGroup = new LabelDiv(name);
    this.label = new Label(name, labelText);
    this.buttons = new Buttons(name, buttonInfo);
    this.label.setButtons(this.buttons);

    /* 라벨그룹에 라벨 넣기 */
    this.labelGroup.setLabel(this.label);

    /* 전체에 라벨그룹 넣기 */
    this.listGroup.setLabelDiv(this.labelGroup);


    /* 테이블 그룹 */
    this.tableGroup = new TableDiv(name);

    /* 헤드테이블 div 에 헤드 테이블 넣기 */
    this.tableHead = new TableHeadDiv();
    this.headTable = new HeadTable(columns);

    this.tableHead.setHeadTable(this.headTable);

    /* 바디 테이블 div 에 바디 테이블 넣기 */
    this.tableBodyDiv = new TableBodyDiv();

    this.bodyTable = new BodyTable(columns);
    this.tableBodyDiv.setBodyTable(this.bodyTable);

    /* 테이블 그룹에 헤드&바디 테이블 넣기 */
    this.tableGroup.setTable(this.tableHead, this.tableBodyDiv);

    /* 전체 그룹에 테이블 넣기 */
    this.listGroup.setTable(this.tableGroup);

    return this.listGroup;
}