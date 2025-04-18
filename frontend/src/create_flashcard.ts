enum CanvasMode {
    latex,
    pencil,
    image
}

let canvasMode: CanvasMode = CanvasMode.pencil;
let drawing: boolean = false;

let pencilSize: number = 7;

let lastX = 0;
let lastY = 0;


const latexButton = document.getElementById("select-latex") as HTMLButtonElement;
const pencilButton = document.getElementById("select-pencil") as HTMLButtonElement;
const drawingCanvas = document.getElementById("drawing-canvas") as HTMLCanvasElement;
const canvasCtx = drawingCanvas.getContext("2d");
const pencilSizeValue = document.getElementById('pencil-size-value') as HTMLLabelElement;
const pencilSizeInput = document.getElementById("pencil-size") as HTMLInputElement;

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
function focusCanvasModeButton(buttonToUnfocus: HTMLButtonElement, buttonToFocus: HTMLButtonElement) {
    buttonToFocus.classList.remove("opacity-65");
    buttonToUnfocus.classList.remove("opacity-100");

    buttonToFocus.classList.add("opacity-100");
    buttonToUnfocus.classList.add("opacity-65");
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


// event listener

if (pencilSizeInput && pencilSizeValue) {
    pencilSizeInput.addEventListener('input', (e) => {
        const target = e.target as HTMLInputElement;
        pencilSizeValue.textContent = target.value;

        if(canvasCtx) canvasCtx.lineWidth = parseInt(target.value);
        
        pencilSize = parseInt(target.value);
        console.log(pencilSize);
    });
  }


if (pencilButton) {
    pencilButton.addEventListener('click', () => {
    canvasMode = CanvasMode.pencil;
    
    focusCanvasModeButton(latexButton, pencilButton );
  });
}

if (latexButton) {
    latexButton.addEventListener('click', () => {
    canvasMode = CanvasMode.latex
    focusCanvasModeButton(pencilButton, latexButton);
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

