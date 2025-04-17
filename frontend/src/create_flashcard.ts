
enum CanvasMode {
    latex,
    pencil
}

let canvasMode: CanvasMode = CanvasMode.pencil;
let drawing: boolean = false;

let lastX = 0;
let lastY = 0;


const latexButton = document.getElementById("select-latex") as HTMLButtonElement;
const pencilButton = document.getElementById("select-pencil") as HTMLButtonElement;
const drawingCanvas = document.getElementById("drawing-canvas") as HTMLCanvasElement;
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


function selectPencilButtonAnimation(pencilButton: HTMLButtonElement, latexButton: HTMLButtonElement) {
    pencilButton.classList.remove("opacity-65");
    latexButton.classList.remove("opacity-100");

    pencilButton.classList.add("opacity-100");
    latexButton.classList.add("opacity-65");
}

function selectLatexButtonAnimation(pencilButton: HTMLButtonElement, latexButton: HTMLButtonElement) {
    latexButton.classList.remove("opacity-65");
    pencilButton.classList.remove("opacity-100");

    latexButton.classList.add("opacity-100");
    pencilButton.classList.add("opacity-65");
}

function drawSmoothLine(x: number, y: number) {
    if (!drawing) return;
    if(!canvasCtx) return;
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
    
    selectPencilButtonAnimation(pencilButton, latexButton);
  });
}

if (latexButton) {
    latexButton.addEventListener('click', () => {
    canvasMode = CanvasMode.latex
    selectLatexButtonAnimation(pencilButton, latexButton);
    });
}

if(drawingCanvas) {
    drawingCanvas.addEventListener('mousedown', (e) => {
        if(e.button != 0) return;
        lastX = e.offsetX;
        lastY = e.offsetY;
        drawing = true;
        
      });
      drawingCanvas.addEventListener('mousemove', (e) => {
        if(drawing) {
            drawSmoothLine(e.offsetX, e.offsetY)
        }
      });


      drawingCanvas.addEventListener('mouseup', () => {
        drawing = false;
    });

    drawingCanvas.addEventListener('mouseout', () => {
        drawing = false;
    });
}

