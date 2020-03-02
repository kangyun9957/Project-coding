const toDoForm = document.querySelector(".js-toDoForm"),
  toDoInput = toDoForm.querySelector("input"),
  toDoList = document.querySelector(".js-toDoList");

const TODOS_LS = "todos";
let todos = [];

function deleteTODO(event) {
  const btn = event.target;
  const li = btn.parentNode;
  toDoList.removeChild(li);
  const cleantoDos = todos.filter(function(TODO) {
    return TODO.id !== parseInt(li.id); //필터링해서 false일경우 리스트에서 제외
  });
  todos = cleantoDos;
  savedtodo();
}
function paintToDo(text) {
  const li = document.createElement("li");
  const delBtn = document.createElement("button");
  const newid = todos.length + 1;
  delBtn.innerHTML = '<i class="fas fa-times-circle"></i>';
  delBtn.addEventListener("click", deleteTODO);
  const span = document.createElement("span");
  span.innerText = text;
  li.appendChild(span);
  li.appendChild(delBtn);
  li.id = newid;
  toDoList.appendChild(li);
  const todosObject = {
    text: text,
    id: newid
  };
  todos.push(todosObject);
  savedtodo();
}

function savedtodo() {
  localStorage.setItem(TODOS_LS, JSON.stringify(todos));
}
function handleSubmit(event) {
  event.preventDefault();
  const currentValue = toDoInput.value;
  paintToDo(currentValue);
  toDoInput.value = "";
}
function loadToDos() {
  const loadedtoDos = localStorage.getItem(TODOS_LS);
  if (loadedtoDos !== null) {
    const parsedto = JSON.parse(loadedtoDos);
    parsedto.forEach(function(TODO) {
      paintToDo(TODO.text);
    });
  }
}
function init() {
  loadToDos();
  toDoForm.addEventListener("submit", handleSubmit);
}
init();
