const menuData = {
    "default-logo": [
        { image: "/picture/notion/notion-logo.png" }
    ],
    "devops-menu": [
        { title: "DevOps", image: "/picture/notion/item/도둑.jpg", url: "https://sable-mars-102.notion.site/DevOps-17ccb42f28df808e8aa0fa4c2fd75c0d?pvs=4" }
    ],
    "os-menu": [
        { title: "Linux", image: "/picture/notion/item/linux.png", url: "https://sable-mars-102.notion.site/Linux-17ccb42f28df80cd8f87c2eced608f8a?pvs=4" }
    ],
    "server-menu": [
        { title: "Cloud", image: "/picture/notion/item/AWSGCPAZURE.png", url: "https://sable-mars-102.notion.site/Cloud-17ccb42f28df806f865fc0478df7407f?pvs=4" },
        { title: "Network", image: "/picture/notion/item/network.png", url: "https://sable-mars-102.notion.site/Cisco-17ccb42f28df80679813f664b3229f84?pvs=4" }
    ],
    "management-menu": [
        { title: "Docker & k8s", image: "/picture/notion/item/docker&k8s.jpg", url: "https://sable-mars-102.notion.site/Dokcer-K8S-17ccb42f28df80e5ae9bedb6aefee068?pvs=4" },
        { title: "CI/CD", image: "/picture/notion/item/cicd.jpg", url: "https://sable-mars-102.notion.site/CI-CD-17ccb42f28df8076871be9b3273e11eb?pvs=4" },
        { title: "IaC", image: "/picture/notion/item/iac.png", url: "https://sable-mars-102.notion.site/IaC-17ccb42f28df8078ae8dfd58929a2837?pvs=4" },
        { title: "Git", image: "/picture/notion/item/git.png", url: "https://sable-mars-102.notion.site/Git-Github-17ccb42f28df80af94f7f87a40b3fc9d?pvs=4" }
    ],
    "development-menu": [
        { title: "Development Language", image: "/picture/notion/item/development.png", url: "https://sable-mars-102.notion.site/Language-17ccb42f28df8005a1ece1a9b6048ae7?pvs=4" }
    ],
    "handon-menu": [
        { title: "Project-Based Learning", image: "/picture/notion/item/handon.png", url: "https://sable-mars-102.notion.site/Hand-On-17ccb42f28df807282e6d02c724130f2?pvs=4" }
    ]
};

document.addEventListener('DOMContentLoaded', () => {
    const defaultLogo = menuData["default-logo"][0].image;
    const logoContainer = document.getElementById('logo-container');
    logoContainer.style.backgroundImage = `url(${defaultLogo})`;
});

document.querySelector('.logo-container').addEventListener('click', () => {
    animateLogoAndShowMenu();
});

function animateLogoAndShowMenu() {
    const logoContainer = document.querySelector('.logo-container');
    const menuContainer = document.querySelector('.menu-container');

    logoContainer.classList.add('hidden');

    setTimeout(() => {
        menuContainer.classList.add('active');
    }, 300);
}

function setLogoBackground(menuId) {
    const logoContainer = document.getElementById('logo-container');
    if (menuData[menuId] && menuData[menuId][0]) {
        const imageUrl = menuData[menuId][0].image;
        logoContainer.style.backgroundImage = `url(${imageUrl})`;
    }
}

function showMenu(menuId) {
    setLogoBackground(menuId);
    const menuItems = menuData[menuId] || [];
    const submenuContent = document.getElementById('submenu-content');
    submenuContent.innerHTML = "";

    menuItems.forEach(item => {
        const menuItem = document.createElement('div');
        menuItem.innerHTML = `
            <a href="${item.url}" target="_blank" class="block w-full bg-white rounded-lg shadow-md p-4">
                <img src="${item.image}" alt="${item.title}" class="w-full h-40 object-cover rounded-t-lg">
                <h2 class="mt-2 text-lg font-bold">${item.title}</h2>
            </a>
        `;
        submenuContent.appendChild(menuItem);
    });

    document.getElementById('submenu-overlay').classList.remove('hidden');
}

function closeMenu() {
    document.getElementById('submenu-overlay').classList.add('hidden');
}
