
enum CanvasMode {
    latex,
    pencil
}

let canvasMode: CanvasMode = CanvasMode.pencil;


const selectLatexButton = document.getElementById("select-latex") as HTMLButtonElement;
const selectPencilButton = document.getElementById("select-pencil") as HTMLButtonElement;
const drawingCanvas = document.getElementById("drawing-canvas") as HTMLCanvasElement;

if (selectPencilButton) {
  selectPencilButton.addEventListener('click', () => {
    canvasMode = CanvasMode.pencil;
    alert('Clicked!');
  });
}

if (selectLatexButton) {
    selectLatexButton.addEventListener('click', () => {
        canvasMode = CanvasMode.latex
      alert('Clicked!');
      console.log(canvasMode == CanvasMode.latex)
    });
}
