document.addEventListener("DOMContentLoaded", () => {
    const playButton = document.getElementById("play-album-button");
    if (!playButton) return;

    playButton.addEventListener("click", () => {
        const data = playButton.getAttribute("data-playlist");

        if (!data) {
            console.warn("Нет данных плейлиста в кнопке");
            return;
        }

        let playlist;
        try {
            playlist = JSON.parse(data);
        } catch (e) {
            console.error("Ошибка парсинга плейлиста:", e);
            return;
        }

        if (typeof setPlaylist === "function") {
            setPlaylist(playlist, 0);
        } else {
            console.error("Функция setPlaylist не найдена");
        }
    });
});
