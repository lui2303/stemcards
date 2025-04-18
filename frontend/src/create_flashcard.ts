enum CanvasMode {
    latex,
    pencil,
    image
}

enum EraserSize {
    small,
    medium,
    large,
    none
}

let canvasMode: CanvasMode = CanvasMode.pencil;
let drawing: boolean = false;

let erasing: boolean = false;
let eraserSize: EraserSize = EraserSize.none;


let lastX = 0;
let lastY = 0;


const latexButton = document.getElementById("select-latex") as HTMLButtonElement;
const pencilButton = document.getElementById("select-pencil") as HTMLButtonElement;

const drawingCanvas = document.getElementById("drawing-canvas") as HTMLCanvasElement;
const canvasCtx = drawingCanvas.getContext("2d");

const pencilSizeValue = document.getElementById('pencil-size-value') as HTMLLabelElement;
const pencilSizeInput = document.getElementById("pencil-size") as HTMLInputElement;

const smallEraserButton = document.getElementById("small-eraser") as HTMLButtonElement;
const mediumEraserButton = document.getElementById("medium-eraser") as HTMLButtonElement;
const largeEraserButton = document.getElementById("large-eraser") as HTMLButtonElement;
const stopEraserButton = document.getElementById('draw') as HTMLButtonElement;


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
    canvasCtx.moveTo(lastX, lastY);
    canvasCtx.lineTo(x, y);
    canvasCtx.stroke();
    lastX = x;
    lastY = y;
}

function erase(eraserSize: number, clientX: number, clientY: number) {
    if(!canvasCtx) return

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
        const target = e.target as HTMLInputElement;
        pencilSizeValue.textContent = target.value;

        if(canvasCtx) canvasCtx.lineWidth = parseInt(target.value);
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
        if(eraserSize != EraserSize.none) {
            erasing = true;
            return;
        }

        lastX = e.offsetX;
        lastY = e.offsetY;
        drawing = true;
        
      });
      drawingCanvas.addEventListener('mousemove', (e) => {
        if(!canvasCtx) return;

        if(drawing) {
            drawSmoothLine(e.offsetX, e.offsetY)
        }
        console.log(erasing);
        console.log(eraserSize);
        
        
        if (erasing) {
            switch(eraserSize) {
                case EraserSize.small:
                    erase(20, e.clientX, e.clientY);
                    break;
                case EraserSize.medium:
                    erase(40, e.clientX, e.clientY);
                    break;
                case EraserSize.large:
                    erase(60, e.clientX, e.clientY);
                    console.log("Here")
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

if(smallEraserButton) {
    smallEraserButton.addEventListener('click', () => {
        eraserSize = EraserSize.small;
    });
}

if(mediumEraserButton) {
    mediumEraserButton.addEventListener('click', () => {
        eraserSize = EraserSize.medium;
    });
}

if(largeEraserButton) {
    largeEraserButton.addEventListener('click', () => {
        eraserSize = EraserSize.large;
    });
}

if(stopEraserButton) {
    stopEraserButton.addEventListener('click', () => {
        erasing = false;
        eraserSize = EraserSize.none;
    })
}

