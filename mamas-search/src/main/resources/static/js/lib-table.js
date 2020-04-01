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
function ListMaker(name) {
    this.listName = name + '-list-group';
    this.listGroupDiv = newElement('div', {"name": this.listName + '-list-group'});
}

ListMaker.prototype.getList = function () {
    return this.listGroupDiv;
};

ListMaker.InfoLabel = function (labelName) {
    this.labelName = labelName;
    this.labelDiv = newElement('div', {"name": this.labelName + '-list-label'});
    this.listGroupDiv.append(this.labelDiv);
};

ListMaker.InfoLabel.prototype.setLabel = function (tableName, labelText) {
    var label = newElement("label", {"for": tableName}, labelText);
    this.labelDiv.append(label);

    return this;
};

ListMaker.InfoLabel.prototype.setButtons = function (buttonNumber, buttonInfo) {
    if (buttonNumber < 1)
        return;

    var buttonDiv = newElement("div", {"class": "[[]]"});

    for (var buttonName in buttonInfo) {// [[{attributes}, text], [{attributes}, text], [{attributes}, text]]
        var button = newElement("button", buttonInfo[0][buttonName], buttonInfo[1]);
        buttonDiv.append(button);
    }

    var label = this.labelDiv.find('label');

    label.append(buttonDiv);
    return this;
};

ListMaker.Table = function (name, columns) {
    this.tableName = name;
    this.tableDiv = newElement(
        'div',
        {
            "name": name + '-table-group',
            "class": "mb-3 dataTables_scroll"
        });
    this.columns = columns;
};

ListMaker.Table.prototype.addColumns = function(row, columns) {
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

ListMaker.Table.prototype.addRows = function(table, rows) {
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

ListMaker.Table.prototype.addHead = function(attributes) {
    let columns = this.columns;
    var thead = document.createElement('thead');

    var tr = document.createElement('tr');
    tr.setAttribute('role', 'row');

    for (var column in columns) {
        var th = document.createElement('th');
        for (var name in attributes) {
            th.setAttribute(name, attributes[name]);
        }
        th.innerText = column;

        tr.append(th);
    }
    thead.append(tr);
};

ListMaker.Table.prototype.addBody = function() {
    var tbody = document.createElement('tbody');
};

ListMaker.Table.prototype.setTableHead = function (headerInfo) {
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

    var thead = this.addHead();
    var tbody = this.addBody();

    table.append(thead);
    table.append(tbody);

    tableHeadInnerDiv.append(table);
    tableHeadDiv.append(tableHeadInnerDiv);
    this.tableDiv.append(tableHeadDiv);

    return this;
};

ListMaker.Table.prototype.setTableBody = function () {

};