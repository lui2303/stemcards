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


interface Point {
    x: number;
    y: number;
}

interface Stroke{
    tool: "pencil" | "eraser";
    points: Array<Point>;
    thickness: number;
    color?: string;

}



let canvasMode: CanvasMode = CanvasMode.pencil;
let drawing: boolean = false;

let erasing: boolean = false;
let eraserSize: EraserSize = EraserSize.none;

let strokes: Array<Stroke | null> = new Array();

let currentStroke: Stroke | null = null;


let lastX = 0;
let lastY = 0;


const latexButton = document.getElementById("select-latex") as HTMLButtonElement;
const pencilButton = document.getElementById("select-pencil") as HTMLButtonElement;

const drawingQuestionCanvas = document.getElementById("drawing-question-canvas") as HTMLCanvasElement;
const drawingQuestionCanvasCtx = drawingQuestionCanvas.getContext("2d");

const drawingAnswerCanvas = document.getElementById("drawing-answer-canvas") as HTMLCanvasElement;
const drawingAnswerCanvasCtx = drawingAnswerCanvas.getContext("2d");

const pencilSizeValue = document.getElementById('pencil-size-value') as HTMLLabelElement;
const pencilSizeInput = document.getElementById("pencil-size") as HTMLInputElement;

const smallEraserButton = document.getElementById("small-eraser") as HTMLButtonElement;
const mediumEraserButton = document.getElementById("medium-eraser") as HTMLButtonElement;
const largeEraserButton = document.getElementById("large-eraser") as HTMLButtonElement;
const stopEraserButton = document.getElementById('draw') as HTMLButtonElement;

const pencilColorInput = document.getElementById("pencil-color") as HTMLInputElement;

const continueButton = document.getElementById('continue-to-answer') as HTMLButtonElement;
const previousButton = document.getElementById('back-to-question') as HTMLButtonElement
const finishFlashcard = document.getElementById('finish-flashcard') as HTMLButtonElement
// TODO: add return to previous side button

let answer: boolean = false; // describes the flashcard side currently on
let currentCanvas: HTMLCanvasElement = drawingQuestionCanvas;
let currentCanvasCtx = drawingQuestionCanvasCtx;



const ratio = window.devicePixelRatio || 1;

drawingQuestionCanvas.width = drawingQuestionCanvas.offsetWidth * ratio;
drawingQuestionCanvas.height = drawingQuestionCanvas.offsetHeight * ratio;

if (drawingQuestionCanvasCtx) {
    drawingQuestionCanvasCtx.scale(ratio, ratio);
    drawingQuestionCanvasCtx.lineJoin = 'round';
    drawingQuestionCanvasCtx.lineCap = 'round';
    drawingQuestionCanvasCtx.lineWidth = 7;
}

drawingAnswerCanvas.width = drawingAnswerCanvas.offsetWidth * ratio;
drawingAnswerCanvas.height = drawingAnswerCanvas.offsetHeight * ratio;

if (drawingAnswerCanvasCtx) {
    drawingAnswerCanvasCtx.scale(ratio, ratio);
    drawingAnswerCanvasCtx.lineJoin = 'round';
    drawingAnswerCanvasCtx.lineCap = 'round';
    drawingAnswerCanvasCtx.lineWidth = 7;
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
    if(!currentCanvasCtx) return;
    currentCanvasCtx.beginPath();
    currentCanvasCtx.moveTo(lastX, lastY);
    currentCanvasCtx.lineTo(x, y);
    currentCanvasCtx.stroke();
    if(currentStroke) currentStroke.points.push({"x": x, "y": y})

    lastX = x;
    lastY = y;
}

function erase(eraserSize: number, clientX: number, clientY: number) {
    if(!currentCanvasCtx) return

    const rect = drawingQuestionCanvas.getBoundingClientRect();
    const x = clientX - rect.left;
    const y = clientY - rect.top;
    
    currentCanvasCtx.globalCompositeOperation = 'destination-out';
    currentCanvasCtx.beginPath();
    currentCanvasCtx.arc(x, y, eraserSize, 0, 2 * Math.PI);
    currentCanvasCtx.fill();
    currentCanvasCtx.globalCompositeOperation = 'source-over';

    if(currentStroke) currentStroke.points.push({"x": x, "y": y})
}

function canvas2SVG(strokes: Array<Stroke>, height: number, width: number) {
    const svgElements = strokes.map((stroke) => {
        if (stroke?.points.length === 0) return '';
    
        if (stroke?.tool === 'pencil') {
          const d = stroke.points.map((p, i) => `${i === 0 ? 'M' : 'L'} ${p.x} ${p.y}`).join(' ');
          const strokeColor = stroke.color ?? '#000';
    
          return `<path d="${d}" stroke="${strokeColor}" stroke-width="${stroke.thickness}" fill="none" stroke-linecap="round" stroke-linejoin="round"/>`;
        } else {
          return stroke?.points.map(p =>
            `<circle cx="${p.x}" cy="${p.y}" r="${stroke?.thickness}" fill="white" />`
          ).join('\n');
        }
      });
      return `
    <svg xmlns="http://www.w3.org/2000/svg" width="${width}" height="${height}" viewBox="0 0 ${width} ${height}">
      ${svgElements.join('\n  ')}
    </svg>
    `.trim();
}

// event listener

if (pencilSizeInput && pencilSizeValue) {
    pencilSizeInput.addEventListener('input', (e) => {
        const target = e.target as HTMLInputElement;
        pencilSizeValue.textContent = target.value;

        if(currentCanvasCtx) currentCanvasCtx.lineWidth = parseInt(target.value);
    });
  }

if(pencilColorInput) {
    pencilColorInput.addEventListener('input', (e) => {
        const target = e.target as  HTMLInputElement;
        if(currentCanvasCtx) currentCanvasCtx.strokeStyle = target.value;
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

function handleMouseDown (e: MouseEvent){
        if(!currentCanvasCtx) return;

        if(e.button != 0) return;
        if(eraserSize != EraserSize.none) {
            erasing = true;
            currentStroke = {tool: "eraser", color: undefined, points: new Array<Point>(), thickness: 20+eraserSize*20}
            return;
        }

        currentStroke = {tool: "pencil", color: currentCanvasCtx.strokeStyle.toString(), thickness: currentCanvasCtx.lineWidth, points: new Array<Point>()}
        
        lastX = e.offsetX;
        lastY = e.offsetY;
        drawing = true;
        
}

function handleMouseMove(e: MouseEvent) {
        if(drawing) {
            console.log(e.offsetX, e.offsetY)
            drawSmoothLine(e.offsetX, e.offsetY)
        }
        
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
                    break;
            }

        }
}

function handleMouseUp (e: MouseEvent) {
    console.log(currentStroke);
    drawing = false;
    erasing = false;

    if(!currentStroke) return;
    if(currentStroke.points.length > 0)strokes.push(currentStroke);
    strokes.push(currentStroke);
    currentStroke = null;
}

function handleMouseOut (e: MouseEvent) {
    drawing = false;
    erasing = false;

    console.log(currentStroke)
    if(!currentStroke) return;
    if(currentStroke.points.length > 0)strokes.push(currentStroke);

    currentStroke = null;
}

if(drawingQuestionCanvas){
    drawingQuestionCanvas.addEventListener('mousedown', handleMouseDown);
    drawingQuestionCanvas.addEventListener('mousemove', handleMouseMove);
    drawingQuestionCanvas.addEventListener('mouseup', handleMouseUp);
    drawingQuestionCanvas.addEventListener('mouseout', handleMouseOut);
}

if(drawingAnswerCanvas){
    drawingAnswerCanvas.addEventListener('mousedown', handleMouseDown);
    drawingAnswerCanvas.addEventListener('mousemove', handleMouseMove);
    drawingAnswerCanvas.addEventListener('mouseup', handleMouseUp);
    drawingAnswerCanvas.addEventListener('mouseout', handleMouseOut);
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

function toggleCanvas() {
    if(answer) {
        drawingAnswerCanvas.classList.add('invisible')
        drawingQuestionCanvas.classList.remove('invisible')
        currentCanvas = drawingQuestionCanvas;
        currentCanvasCtx = drawingQuestionCanvasCtx;
        answer=false;
        drawing=false;
        erasing=false;
    } else {
        
        drawingQuestionCanvas.classList.add('invisible')
        drawingAnswerCanvas.classList.remove('invisible')
        currentCanvas = drawingAnswerCanvas;
        currentCanvasCtx = drawingAnswerCanvasCtx;
        answer=true;
        drawing=false;
        erasing=false;
    }
}

continueButton?.addEventListener('click', () => {
    previousButton.classList.remove('hidden')
    continueButton.classList.add('hidden')
    finishFlashcard.classList.remove('hidden')
    toggleCanvas();
} )

previousButton?.addEventListener('click', () => {
    previousButton.classList.add('hidden')
    continueButton.classList.remove('hidden')
    finishFlashcard.classList.add('hidden')
    toggleCanvas()
})


finishFlashcard?.addEventListener('click', () => {
    console.log("strokes")
})

