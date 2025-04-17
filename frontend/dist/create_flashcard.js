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
function drawCanvasGrid(drawingCanvas) {
    var ctx = drawingCanvas.getContext("2d");
    if (!ctx) {
        return;
    }
    console.log("canvas context exists");
    ctx.stroke();
    for (let x = 25; x < drawingCanvas.width; x += 25) {
        for (let y = 25; y < drawingCanvas.height; y += 25) {
            ctx.beginPath();
            ctx.arc(x, y, 1, 0, Math.PI * 2);
            ctx.fillStyle = "black";
            ctx.fill();
        }
    }
    drawingCanvas.width;
    drawingCanvas.height;
}
drawCanvasGrid(drawingCanvas);
