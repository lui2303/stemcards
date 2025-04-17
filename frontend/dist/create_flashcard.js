"use strict";
var CanvasMode;
(function (CanvasMode) {
    CanvasMode[CanvasMode["latex"] = 0] = "latex";
    CanvasMode[CanvasMode["pencil"] = 1] = "pencil";
    CanvasMode[CanvasMode["image"] = 2] = "image";
})(CanvasMode || (CanvasMode = {}));
let canvasMode = CanvasMode.pencil;
let drawing = false;
let lastX = 0;
let lastY = 0;
const latexButton = document.getElementById("select-latex");
const pencilButton = document.getElementById("select-pencil");
const drawingCanvas = document.getElementById("drawing-canvas");
const canvasCtx = drawingCanvas.getContext("2d");
const ratio = window.devicePixelRatio || 1;
drawingCanvas.width = drawingCanvas.offsetWidth * ratio;
drawingCanvas.height = drawingCanvas.offsetHeight * ratio;
if (canvasCtx) {
    canvasCtx.scale(ratio, ratio); // Scale the context to match the display resolution
    canvasCtx.lineJoin = 'round';
    canvasCtx.lineCap = 'round';
    canvasCtx.lineWidth = 5;
}
function focusCanvasModeButton(buttonToUnfocus, buttonToFocus) {
    buttonToFocus.classList.remove("opacity-65");
    buttonToUnfocus.classList.remove("opacity-100");
    buttonToFocus.classList.add("opacity-100");
    buttonToUnfocus.classList.add("opacity-65");
}
function drawSmoothLine(x, y) {
    if (!drawing)
        return;
    if (!canvasCtx)
        return;
    canvasCtx.beginPath();
    canvasCtx.moveTo(lastX, lastY); // Move to the previous point
    canvasCtx.lineTo(x, y); // Draw a line to the new point
    canvasCtx.stroke(); // Apply the stroke
    lastX = x; // Update the last position
    lastY = y;
}
if (pencilButton) {
    pencilButton.addEventListener('click', () => {
        canvasMode = CanvasMode.pencil;
        focusCanvasModeButton(latexButton, pencilButton);
    });
}
if (latexButton) {
    latexButton.addEventListener('click', () => {
        canvasMode = CanvasMode.latex;
        focusCanvasModeButton(pencilButton, latexButton);
    });
}
if (drawingCanvas) {
    drawingCanvas.addEventListener('mousedown', (e) => {
        if (e.button != 0)
            return;
        lastX = e.offsetX;
        lastY = e.offsetY;
        drawing = true;
    });
    drawingCanvas.addEventListener('mousemove', (e) => {
        if (drawing) {
            drawSmoothLine(e.offsetX, e.offsetY);
        }
    });
    drawingCanvas.addEventListener('mouseup', () => {
        drawing = false;
    });
    drawingCanvas.addEventListener('mouseout', () => {
        drawing = false;
    });
}
