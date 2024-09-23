

function showNavOptions(sectionId){

    const section = document.getElementById(sectionId);

    if(section.classList.contains("fun-nav-hidden")){
        section.classList.remove("fun-nav-hidden");
        section.classList.add("fun-nav-shown");
    } else {
        section.classList.remove("fun-nav-shown");
        section.classList.add("fun-nav-hidden");
    }

    const showedSections = document.querySelectorAll(".fun-nav-shown");

    showedSections.forEach( sect => {
        sect.classList.remove("fun-nav-shown");
        sect.classList.add("fun-nav-hidden");
    });
}