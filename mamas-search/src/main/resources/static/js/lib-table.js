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

function TableMaker(tableName, columnNumber) {
    this.tableName = tableName+'-list-group';
    this.columnNumber = columnNumber;
    this.columns;
    this.totalDiv = newElement(this.tableName);
}



TableMaker.InfoLabel = function (labelName) {
    this.labelName = labelName;
    this.labelDiv = newElement(this.labelName);
};

TableMaker.InfoLabel.prototype.setLabel = function (tableName) {
    var label = newElement("label", '[[name]]', {"for": tableName});

    this.labelDiv.append(label);

    return this;
};

TableMaker.InfoLabel.prototype.setButtons = function (buttonNumber, buttonInfo) {
    if (buttonNumber < 1)
        return;

    var buttonDiv = newElement("div", '[[name]]');

    for (var buttonName in buttonInfo) {
        var button = newElement("button", buttonName, buttonInfo[buttonName]);
        buttonDiv.append(button);
    }

    var label = this.labelDiv.find('label');

    label.append(buttonDiv);
    return this;
};

TableMaker.Table = function(tableName) {
    this.tableDiv = newElement();
};

TableMaker.Table.prototype.setTableHeader = function (columns) {
    var tableHeaderInner = newElement(this.tableName+'header-head', );
    if (this.totalElement.children('.infolabel')) {

        this.totalElement.append(tableHeaderInner)
    } else {
        this.totalElement.prepend(tableHeaderInner)
    }

    if (columns.length === this.columnNumber) {
        for (var column in columns) {
            co
        }
    } else {
        throw 'Illegal Column Number Information'
    }

};

TableMaker.Table.prototype.setTableBody = function () {

};