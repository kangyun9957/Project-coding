const back = document.querySelector("body");

const image = 3;

function paintimage(IMG) {
  const image = new Image();

  image.src = `${IMG + 1}.jpg`;
  image.classList.add("bgimage");
  back.appendChild(image);
}

function imagenumber() {
  const randnum = Math.floor(Math.random() * image);

  return randnum;
}
function init() {
  const number = imagenumber();
  paintimage(number);
}

init();
