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

TableHeadDiv.prototype.setHeadTable = function(headTable) {
    this.tableHeadInnerDiv.append(headTable);
};

TableHeadDiv.prototype.getTableHeadDiv = function() {
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

TableBodyDiv = function (attributes) {
    this.tableBodyDiv = newElement(
        'div',
        attributes
    );
};

TableBodyDiv.prototype.setBodyTable = function(bodyTable) {
    this.tableBodyDiv.append(bodyTable);
};

TableBodyDiv.prototype.getTableBodyDiv = function() {
    return this.tableBodyDiv;
};

BodyTable = function(columns) {
    this.columns = columns;
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

BodyTable.prototype.addThead = function() {
    var columns = this.columns;
    var attributes = {"class": "th-sm sorting table-body-header"};
    var tr = newElement('tr');
    for (var column in columns) {
        var th = newElement('th', attributes, column);
        this.tr.append(th);
    }
    this.thead.append(tr);
};

/// todo 여기서부터 하면됨.
BodyTable.prototype.addTbody = function(rows) {
    var columns = this.columns;

    var number = 0;
    for (var row in rows) {
        number++;
        var tr = newElement(
            'tr',
            {"tabindex":"0"},
        );
        var th = newElement(
            'th',
            {"scope": "row"},
            number
        );
        tr.append(th);
        for (var column in columns) {
            var td = newElement(
                'td',
                {},
                column
            );
            tr.append(td);
        }
        this.tbody.append(tr);
    }
};