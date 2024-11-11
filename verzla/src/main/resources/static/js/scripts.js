/*!
 * Start Bootstrap - SB UI Kit Pro v2.0.5 (https://shop.startbootstrap.com/product/sb-ui-kit-pro)
 * Copyright 2013-2024 Start Bootstrap
 * Licensed under SEE_LICENSE (https://github.com/BlackrockDigital/sb-ui-kit-pro/blob/master/LICENSE)
 */
window.addEventListener('DOMContentLoaded', (event) => {

  // Enable tooltips globally
  var tooltipTriggerList = [].slice.call(
    document.querySelectorAll('[data-bs-toggle="tooltip"]')
  );
  var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
  });

  // Enable popovers globally
  var popoverTriggerList = [].slice.call(
    document.querySelectorAll('[data-bs-toggle="popover"]')
  );
  var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
    return new bootstrap.Popover(popoverTriggerEl);
  });

  // Activate Bootstrap scrollspy for the sticky nav component
  const navStick = document.body.querySelector('#navStick');
  if (navStick) {
    new bootstrap.ScrollSpy(document.body, {
      target: '#navStick',
      offset: 82,
    });
  }

  // Collapse Navbar
  // Add styling fallback for when a transparent background .navbar-marketing is scrolled
  var navbarCollapse = function () {
    const navbarMarketingTransparentFixed = document.body.querySelector(
      '.navbar-marketing.bg-transparent.fixed-top'
    );
    if (!navbarMarketingTransparentFixed) {
      return;
    }
    if (window.scrollY === 0) {
      navbarMarketingTransparentFixed.classList.remove('navbar-scrolled');
    } else {
      navbarMarketingTransparentFixed.classList.add('navbar-scrolled');
    }
  };

  // Collapse now if page is not at top
  navbarCollapse();
  // Collapse the navbar when page is scrolled
  document.addEventListener('scroll', navbarCollapse);

  // Toggle the side navigation
  const sidebarToggle = document.body.querySelector('#sidebarToggle');
  if (sidebarToggle) {
    // Uncomment Below to persist sidebar toggle between refreshes
    // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
    //     document.body.classList.toggle('sidenav-toggled');
    // }
    sidebarToggle.addEventListener('click', (event) => {
      event.preventDefault();
      document.body.classList.toggle('sidenav-toggled');
      localStorage.setItem(
        'sb|sidebar-toggle',
        document.body.classList.contains('sidenav-toggled')
      );
    });
  }

  // Close side navigation when width < LG
  const sidenavContent = document.body.querySelector('#layoutSidenav_content');
  if (sidenavContent) {
    sidenavContent.addEventListener('click', (event) => {
      const BOOTSTRAP_LG_WIDTH = 992;
      if (window.innerWidth >= 992) {
        return;
      }
      if (document.body.classList.contains('sidenav-toggled')) {
        document.body.classList.toggle('sidenav-toggled');
      }
    });
  }

  // Add active state to sidbar nav links
  let activatedPath = window.location.pathname.match(/([\w-]+\.html)/, '$1');

  if (activatedPath) {
    activatedPath = activatedPath[0];
  } else {
    activatedPath = 'index.html';
  }

  const targetAnchors = document.body.querySelectorAll(
    '[href="' + activatedPath + '"].nav-link'
  );

  targetAnchors.forEach((targetAnchor) => {
    let parentNode = targetAnchor.parentNode;
    while (parentNode !== null && parentNode !== document.documentElement) {
      if (parentNode.classList.contains('collapse')) {
        parentNode.classList.add('show');
        const parentNavLink = document.body.querySelector(
          '[data-bs-target="#' + parentNode.id + '"]'
        );
        parentNavLink.classList.remove('collapsed');
        parentNavLink.classList.add('active');
      }
      parentNode = parentNode.parentNode;
    }
    targetAnchor.classList.add('active');
  });
});
