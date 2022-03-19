$(document).ready(function() {
	const listsSize = $("#listsSize").val();
	const wavesurfer = {};
	const playbtn = {};
	const duration = {};
	const current = {};
	volumeSlider = {};
	volumeIcon = {};
	var timeCalculator = function(value) {
		second = Math.floor(value % 60);
		minute = Math.floor((value / 60) % 60);

		if (second < 10) {
			second = "0" + second;
		}

		return minute + ":" + second;
	};
	for (let i = 1; i <= listsSize; i++) {
		duration[i] = document.getElementById('duration' + i);
		current[i] = document.getElementById('current' + i);
		volumeSlider[i] = document.getElementById('volumeSlider' + i);
		volumeIcon[i] = document.getElementById('volumeIcon' + i);
		player = document.querySelector("#player");
		wave = document.getElementById('wave' + i);
		wavesurfer[i] = WaveSurfer.create({
			container: wave,
			waveColor: "#cdedff",
			progressColor: "#1AAFFF",
			height: 70,
			fillParent: true,
			maxCanvasWidth: 10,
			scrollParent: false,
			responsive: true,
			barWidth: 2,
			barHeight: 1,
			barGap: null
		});
		wavesurfer[i].load("song/sunshine blvd - Ocean Blue.wav");
		playbtn[i] = document.getElementById('playbtn' + i);

		$(playbtn[i]).on('click', function() {
			wavesurfer[i].playPause();
		});

		const toggleMute = () => {
			wavesurfer[i].toggleMute()
			const isMuted = wavesurfer[i].getMute()
			if (isMuted) {
				volumeIcon[i].src = "image/volume_mute_black.png"
				volumeSlider[i].disabled = true
			} else {
				volumeSlider[i].disabled = false
				volumeIcon[i].src = "image/volume_black.png"
			}
		}
		const handleVolumeChange = e => {
			// Set volume as input value divided by 100 NB: Wavesurfer only excepts volume
			// value between 0 - 1
			const volume = e.target.value / 100
			wavesurfer[i].setVolume(volume)
			// Save the value to local storage so it persists between page reloads
			localStorage.setItem("audio-player-volume", volume)
		}
		volumeIcon[i].addEventListener("click", toggleMute)
		volumeSlider[i].addEventListener("input", handleVolumeChange)
		const setVolumeFromLocalStorage = () => {
			// Retrieves the volume from local storage, or falls back to default value of 50
			const volume = localStorage.getItem("audio-player-volume") * 100 || 50
			volumeSlider[i].value = volume
		}

		//load audio duration on load
		wavesurfer[i].on("ready", function(e) {
			duration[i].textContent = timeCalculator(wavesurfer[i].getDuration());
			wavesurfer[i].setVolume(volumeSlider.value / 100)
		});

		//get updated current time on play
		wavesurfer[i].on("audioprocess", function(e) {
			current[i].textContent = timeCalculator(wavesurfer[i].getCurrentTime());
		});

		//change play button to pause on plying
		wavesurfer[i].on("play", function(e) {
			playbtn[i]
				.classList
				.remove("fi-rr-play");
			playbtn[i]
				.classList
				.add("fi-rr-pause");
			playbtn[i].src = "image/pause.png";
		});

		//change pause button to play on pause
		wavesurfer[i].on("pause", function(e) {
			playbtn[i]
				.classList
				.add("fi-rr-play");
			playbtn[i]
				.classList
				.remove("fi-rr-pause");
			playbtn[i].src = "image/play.png";
		});

		//update current time on seek
		wavesurfer[i].on("seek", function(e) {
			current.textContent = timeCalculator(wavesurfer[i].getCurrentTime());
		});
	}
});