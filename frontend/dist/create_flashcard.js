"use strict";
var CanvasMode;
(function (CanvasMode) {
    CanvasMode[CanvasMode["latex"] = 0] = "latex";
    CanvasMode[CanvasMode["pencil"] = 1] = "pencil";
})(CanvasMode || (CanvasMode = {}));
let canvasMode = CanvasMode.pencil;
const latexButton = document.getElementById("select-latex");
const pencilButton = document.getElementById("select-pencil");
const drawingCanvas = document.getElementById("drawing-canvas");
function selectPencilButtonAnimation(pencilButton, latexButton) {
    pencilButton.classList.remove("opacity-65");
    latexButton.classList.remove("opacity-100");
    pencilButton.classList.add("opacity-100");
    latexButton.classList.add("opacity-65");
}
function selectLatexButtonAnimation(pencilButton, latexButton) {
    latexButton.classList.remove("opacity-65");
    pencilButton.classList.remove("opacity-100");
    latexButton.classList.add("opacity-100");
    pencilButton.classList.add("opacity-65");
}
if (pencilButton) {
    if (pencilButton) {
        pencilButton.addEventListener('click', () => {
            canvasMode = CanvasMode.pencil;
            selectPencilButtonAnimation(pencilButton, latexButton);
        });
    }
    if (latexButton) {
        latexButton.addEventListener('click', () => {
            canvasMode = CanvasMode.latex;
            selectLatexButtonAnimation(pencilButton, latexButton);
            console.log(canvasMode == CanvasMode.latex);
        });
    }
}
