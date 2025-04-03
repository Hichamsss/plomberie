document.addEventListener("DOMContentLoaded", function() {
    // Éléments du carrousel
    const carousel = document.querySelector(".carousel-images");
    const slides = document.querySelectorAll(".slide");
    const prevBtn = document.querySelector(".prev");
    const nextBtn = document.querySelector(".next");
    const indicators = document.querySelectorAll(".indicator");
    
    let currentIndex = 0;
    let intervalId;
    const slideCount = slides.length;
    const slideInterval = 5000; // 5 secondes

    // Fonction pour mettre à jour le carrousel
    function updateCarousel() {
        carousel.style.transform = `translateX(-${currentIndex * 100}%)`;
        
        // Mettre à jour les indicateurs
        indicators.forEach((indicator, index) => {
            indicator.classList.toggle("active", index === currentIndex);
        });
    }

    // Aller à un slide spécifique
    function goToSlide(index) {
        currentIndex = (index + slideCount) % slideCount;
        updateCarousel();
        resetInterval();
    }

    // Changer de slide
    function changeSlide(step) {
        goToSlide(currentIndex + step);
    }

    // Réinitialiser l'intervalle
    function resetInterval() {
        clearInterval(intervalId);
        startInterval();
    }

    // Démarrer l'intervalle automatique
    function startInterval() {
        intervalId = setInterval(() => {
            changeSlide(1);
        }, slideInterval);
    }

    // Événements
    prevBtn.addEventListener("click", () => changeSlide(-1));
    nextBtn.addEventListener("click", () => changeSlide(1));

    // Clic sur les indicateurs
    indicators.forEach(indicator => {
        indicator.addEventListener("click", () => {
            const slideIndex = parseInt(indicator.dataset.index);
            goToSlide(slideIndex);
        });
    });

    // Navigation au clavier
    document.addEventListener("keydown", (e) => {
        if (e.key === "ArrowLeft") changeSlide(-1);
        if (e.key === "ArrowRight") changeSlide(1);
    });

    // Démarrer le carrousel
    updateCarousel();
    startInterval();

    // Pause au survol
    carousel.parentElement.addEventListener("mouseenter", () => {
        clearInterval(intervalId);
    });

    carousel.parentElement.addEventListener("mouseleave", () => {
        startInterval();
    });
});