
enum CanvasMode {
    latex,
    pencil
}

let canvasMode: CanvasMode = CanvasMode.pencil;




const latexButton = document.getElementById("select-latex") as HTMLButtonElement;
const pencilButton = document.getElementById("select-pencil") as HTMLButtonElement;
const drawingCanvas = document.getElementById("drawing-canvas") as HTMLCanvasElement;

function selectPencilButtonAnimation(pencilButton: HTMLButtonElement, latexButton: HTMLButtonElement) {
    pencilButton.classList.remove("opacity-65");
    latexButton.classList.remove("opacity-100");

    pencilButton.classList.add("opacity-100");
    latexButton.classList.add("opacity-65")
}

function selectLatexButtonAnimation(pencilButton: HTMLButtonElement, latexButton: HTMLButtonElement) {
    latexButton.classList.remove("opacity-65");
    pencilButton.classList.remove("opacity-100");

    latexButton.classList.add("opacity-100");
    pencilButton.classList.add("opacity-65")
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
    canvasMode = CanvasMode.latex
      selectLatexButtonAnimation(pencilButton, latexButton);
      console.log(canvasMode == CanvasMode.latex)
    });
}
