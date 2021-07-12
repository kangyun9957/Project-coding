const api_KEY = "6d4bd92b0476ae94780e60a66a480b8a";
const lo_Coords = "Coords";

function savelocation(locate) {
  localStorage.setItem(lo_Coords, JSON.stringify(locate));
}

function handlegeosuccess(posit) {
  const latitude = posit.coords.latitude;
  const longitude = posit.coords.longitude;
  const geoobj = {
    latitude: latitude,
    logitude: longitude
  };
  console.log(geoobj);
  savelocation(geoobj);
}
function handlegeoerror() {
  console.log("can't find location");
}
function askForcoords() {
  navigator.geolocation.getCurrentPosition(handlegeosuccess, handlegeoerror);
}
function loadcoords() {
  const currentcoords = localStorage.getItem(lo_Coords);

  if (currentcoords === null) {
    askForcoords();
  } else {
  }
}
function init() {
  loadcoords();
}

init();
