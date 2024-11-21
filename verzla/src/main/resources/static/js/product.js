document.addEventListener('DOMContentLoaded', async () => {
  // Check if the user is logged in
  const isLoggedIn = await checkUserLoginStatus();

  // If logged in, attach event listeners to buttons
  if (isLoggedIn) {
    attachEventListeners();
  }

  // Reattach listeners when navigating back (e.g., from wishlist or cart pages)
  window.addEventListener('popstate', () => {
    attachEventListeners();
  });
});

// Function to check if the user is logged in
async function checkUserLoginStatus() {
  try {
    const response = await fetch('/api/users/me', {
      method: 'GET',
      credentials: 'include',
    });

    if (response.ok) {
      return true;
    } else {
      return false;
    }
  } catch (error) {
    return false;
  }
}

function attachEventListeners() {
  // Use event delegation
  const container = document.getElementById('prodPage');
  if (!container) {
    return;
  }

  container.addEventListener('click', async (event) => {
    // Prevent scrolling
    event.preventDefault();

    const target = event.target;

    if (target.closest('.btn')?.classList.contains('addToWish')) {
      const productId = getProductIdFromCard(target);
      await addToWishlist(productId);
    } else if (target.closest('.btn')?.classList.contains('addToCart')) {
      const productId = getProductIdFromCard(target);
      await addToCart(productId);
    }
  });
}

function getProductIdFromCard(element) {
  const card = element.closest('[data-product-id]');
  const productId = card?.getAttribute('data-product-id');
  return productId;
}

// Function to add product to the wishlist
async function addToWishlist(productId) {
  if (!productId) {
    return;
  }
  try {
    const response = await fetch('/api/wishlist', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ productId: parseInt(productId, 10) }),
      credentials: 'include',
    });

    if (response.ok) {
      alert('Product added to wishlist!');
    } else {
      const error = await response.text();
      alert('Failed to add product to wishlist: ' + error);
    }
  } catch (error) {
    console.error('Error adding to wishlist:', error);
  }
}

// Function to add product to the cart
async function addToCart(productId) {
  if (!productId) {
    console.error('No Product ID provided for cart');
    return;
  }
  try {
    const response = await fetch('/api/cart', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ productId: parseInt(productId, 10) }),
      credentials: 'include',
    });

    if (response.ok) {
      alert('Product added to cart!');
    } else {
      const error = await response.text();
      alert('Failed to add product to cart: ' + error);
    }
  } catch (error) {
    console.error('Error adding to cart:', error);
  }
}