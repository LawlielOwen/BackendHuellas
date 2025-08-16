const barraNavega = document.querySelector('.barraNavegacion');
const botonBarra = document.getElementById('botonBarra');
const barraNavegacionBtn = document.getElementById('sidebar-btn');

function checkWindowSize() {
    if (window.innerWidth > 700) {
       
        document.body.classList.remove('sidebar-hidden');
        barraNavega.classList.remove('minimizar');
    }
}


barraNavegacionBtn.addEventListener('click', () => {
    document.body.classList.toggle('sidebar-visible');
});


if (botonBarra) {
    botonBarra.addEventListener('click', () => {
        if (window.innerWidth > 700) {
            barraNavega.classList.toggle('minimizar');
        }
    });
}
document.querySelectorAll('.dropdown').forEach(dropdown => {
    const button = dropdown.querySelector('.botonOpcion');
    const items = dropdown.querySelectorAll('.dropdown-item');

    items.forEach(item => {
        item.addEventListener('click', (e) => {
            e.preventDefault(); 
            button.textContent = item.textContent;
        });
    });
});

checkWindowSize();
window.addEventListener('resize', checkWindowSize);