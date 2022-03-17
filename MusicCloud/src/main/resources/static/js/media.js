	duration = document.querySelector("#duration");
current = document.querySelector("#current");
playPause = document.querySelector("#playPause");
volumeSlider = document.querySelector("#volumeSlider");
volumeIcon = document.querySelector("#volumeIcon");
player = document.querySelector("#player");
var timeCalculator = function (value) {
    second = Math.floor(value % 60);
    minute = Math.floor((value / 60) % 60);
    
    if (second < 10) {
        second = "0" + second;
    }

    return minute + ":" + second;
};

//start wavesurfer object 
wavesurfer = WaveSurfer.create({
    container: "#wave",
    waveColor: "#cdedff",
    progressColor: "#1AAFFF",
    height: 70,
    fillParent: true,
    maxCanvasWidth: 10,
    scrollParent: false,
    responsive: true,
    barWidth: 2,
    barHeight: 1,
    barGap: null,
      
});

//load audio file
wavesurfer.load("song/sunshine blvd - Ocean Blue.wav");

//play and pause a player
playPause.addEventListener("click", function (e) {
    wavesurfer.playPause();
});


const toggleMute = () => {
  wavesurfer.toggleMute()
  const isMuted = wavesurfer.getMute()
  if (isMuted) {
    volumeIcon.src = "image/volume_mute_black.png"
    volumeSlider.disabled = true
  } else {
    volumeSlider.disabled = false
    volumeIcon.src = "image/volume_black.png"
  }
}
const handleVolumeChange = e => {
  // Set volume as input value divided by 100
  // NB: Wavesurfer only excepts volume value between 0 - 1
  const volume = e.target.value / 100
  wavesurfer.setVolume(volume)
  // Save the value to local storage so it persists between page reloads
  localStorage.setItem("audio-player-volume", volume)
}
volumeIcon.addEventListener("click", toggleMute)
volumeSlider.addEventListener("input", handleVolumeChange)
const setVolumeFromLocalStorage = () => {
  // Retrieves the volume from local storage, or falls back to default value of 50
  const volume = localStorage.getItem("audio-player-volume") * 100 || 50
  volumeSlider.value = volume
}


//load audio duration on load
wavesurfer.on("ready", function (e) {
    duration.textContent = timeCalculator(wavesurfer.getDuration());
    wavesurfer.setVolume(volumeSlider.value / 100)
});

//get updated current time on play
wavesurfer.on("audioprocess", function (e) {
    current.textContent = timeCalculator(wavesurfer.getCurrentTime());
});

//change play button to pause on plying
wavesurfer.on("play", function (e) {
    playPause.classList.remove("fi-rr-play");
    playPause.classList.add("fi-rr-pause");
    playPause.src="image/pause.png";
});

//change pause button to play on pause
wavesurfer.on("pause", function (e) {
    playPause.classList.add("fi-rr-play");
    playPause.classList.remove("fi-rr-pause");
    playPause.src="image/play.png";
});

//update current time on seek
wavesurfer.on("seek", function (e) {
    current.textContent = timeCalculator(wavesurfer.getCurrentTime());
});