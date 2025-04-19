"use strict";
var CanvasMode;
(function (CanvasMode) {
    CanvasMode[CanvasMode["latex"] = 0] = "latex";
    CanvasMode[CanvasMode["pencil"] = 1] = "pencil";
    CanvasMode[CanvasMode["image"] = 2] = "image";
})(CanvasMode || (CanvasMode = {}));
var EraserSize;
(function (EraserSize) {
    EraserSize[EraserSize["small"] = 0] = "small";
    EraserSize[EraserSize["medium"] = 1] = "medium";
    EraserSize[EraserSize["large"] = 2] = "large";
    EraserSize[EraserSize["none"] = 3] = "none";
})(EraserSize || (EraserSize = {}));
let canvasMode = CanvasMode.pencil;
let drawing = false;
let erasing = false;
let eraserSize = EraserSize.none;
let lastX = 0;
let lastY = 0;
const latexButton = document.getElementById("select-latex");
const pencilButton = document.getElementById("select-pencil");
const drawingCanvas = document.getElementById("drawing-canvas");
const canvasCtx = drawingCanvas.getContext("2d");
const pencilSizeValue = document.getElementById('pencil-size-value');
const pencilSizeInput = document.getElementById("pencil-size");
const smallEraserButton = document.getElementById("small-eraser");
const mediumEraserButton = document.getElementById("medium-eraser");
const largeEraserButton = document.getElementById("large-eraser");
const stopEraserButton = document.getElementById('draw');
const pencilColorInput = document.getElementById("pencil-color");
const ratio = window.devicePixelRatio || 1;
drawingCanvas.width = drawingCanvas.offsetWidth * ratio;
drawingCanvas.height = drawingCanvas.offsetHeight * ratio;
if (canvasCtx) {
    canvasCtx.scale(ratio, ratio); // Scale the context to match the display resolution
    canvasCtx.lineJoin = 'round';
    canvasCtx.lineCap = 'round';
    canvasCtx.lineWidth = 7;
}
// functions
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
    canvasCtx.moveTo(lastX, lastY);
    canvasCtx.lineTo(x, y);
    canvasCtx.stroke();
    lastX = x;
    lastY = y;
}
function erase(eraserSize, clientX, clientY) {
    if (!canvasCtx)
        return;
    const rect = drawingCanvas.getBoundingClientRect();
    const x = clientX - rect.left;
    const y = clientY - rect.top;
    canvasCtx.globalCompositeOperation = 'destination-out';
    canvasCtx.beginPath();
    canvasCtx.arc(x, y, eraserSize, 0, 2 * Math.PI);
    canvasCtx.fill();
    canvasCtx.globalCompositeOperation = 'source-over';
}
// event listener
if (pencilSizeInput && pencilSizeValue) {
    pencilSizeInput.addEventListener('input', (e) => {
        const target = e.target;
        pencilSizeValue.textContent = target.value;
        if (canvasCtx)
            canvasCtx.lineWidth = parseInt(target.value);
    });
}
if (pencilColorInput) {
    pencilColorInput.addEventListener('input', (e) => {
        const target = e.target;
        if (canvasCtx)
            canvasCtx.strokeStyle = target.value;
    });
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
        if (eraserSize != EraserSize.none) {
            erasing = true;
            return;
        }
        lastX = e.offsetX;
        lastY = e.offsetY;
        drawing = true;
    });
    drawingCanvas.addEventListener('mousemove', (e) => {
        if (!canvasCtx)
            return;
        if (drawing) {
            drawSmoothLine(e.offsetX, e.offsetY);
        }
        console.log(erasing);
        console.log(eraserSize);
        if (erasing) {
            switch (eraserSize) {
                case EraserSize.small:
                    erase(20, e.clientX, e.clientY);
                    break;
                case EraserSize.medium:
                    erase(40, e.clientX, e.clientY);
                    break;
                case EraserSize.large:
                    erase(60, e.clientX, e.clientY);
                    console.log("Here");
                    break;
            }
        }
    });
    drawingCanvas.addEventListener('mouseup', () => {
        drawing = false;
    });
    drawingCanvas.addEventListener('mouseout', () => {
        drawing = false;
    });
}
if (smallEraserButton) {
    smallEraserButton.addEventListener('click', () => {
        eraserSize = EraserSize.small;
    });
}
if (mediumEraserButton) {
    mediumEraserButton.addEventListener('click', () => {
        eraserSize = EraserSize.medium;
    });
}
if (largeEraserButton) {
    largeEraserButton.addEventListener('click', () => {
        eraserSize = EraserSize.large;
    });
}
if (stopEraserButton) {
    stopEraserButton.addEventListener('click', () => {
        erasing = false;
        eraserSize = EraserSize.none;
    });
}
