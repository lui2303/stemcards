"use strict";
var CanvasMode;
(function (CanvasMode) {
    CanvasMode[CanvasMode["latex"] = 0] = "latex";
    CanvasMode[CanvasMode["pencil"] = 1] = "pencil";
})(CanvasMode || (CanvasMode = {}));
let canvasMode = CanvasMode.pencil;
const selectLatexButton = document.getElementById("select-latex");
const selectPencilButton = document.getElementById("select-pencil");
const drawingCanvas = document.getElementById("drawing-canvas");
if (selectPencilButton) {
    selectPencilButton.addEventListener('click', () => {
        canvasMode = CanvasMode.pencil;
        alert('Clicked!');
    });
}
if (selectLatexButton) {
    selectLatexButton.addEventListener('click', () => {
        canvasMode = CanvasMode.latex;
        alert('Clicked!');
        console.log(canvasMode == CanvasMode.latex);
    });
}
