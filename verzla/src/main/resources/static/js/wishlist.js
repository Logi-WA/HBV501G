document.addEventListener('DOMContentLoaded', async () => {
  // Check if the user is logged in
  const isLoggedIn = await checkUserLoginStatus();

  // If logged in, attach event listeners to buttons
  if (isLoggedIn) {
    attachWishlistEventListeners();
    attachBulkActionEventListeners();
  }

  // Reattach listeners when navigating back (e.g., from wishlist or cart pages)
  window.addEventListener('popstate', () => {
    attachWishlistEventListeners();
    attachBulkActionEventListeners();
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

function attachWishlistEventListeners() {
  // Use event delegation
  const container = document.getElementById('wishlistPage');
  if (!container) {
    return;
  }

  // Add event listener to the container, listening for button clicks
  container.addEventListener('click', async (event) => {
    // Prevent scrolling
    event.preventDefault();

    const target = event.target;

    if (target.closest('.btn')?.classList.contains('removeFromWishlist')) {
      const wishlistItemId = getWishlistItemFromList(target);
      await removeFromWishlist(wishlistItemId);
    } else if (target.closest('.btn')?.classList.contains('addToCart')) {
      const wishlistProductId = getProductIdFromWishlist(target);
      await addToCart(wishlistProductId);
    }
  });
}

function attachBulkActionEventListeners() {
  const addAllToCartBtn = document.querySelector('.addAllToCart');
  const clearWishlistBtn = document.querySelector('.clearWishlist');

  if (addAllToCartBtn) {
    addAllToCartBtn.addEventListener('click', async () => {
      await addAllToCart();
    });
  }

  if (clearWishlistBtn) {
    clearWishlistBtn.addEventListener('click', async () => {
      await clearWishlist();
    });
  }
}

// Helper function to extract wishlist item Id from wishlist list
function getWishlistItemFromList(element) {
  const li = element.closest('[data-witem-id]');
  const wishlistItemId = li?.getAttribute('data-witem-id');
  return wishlistItemId;
}

function getProductIdFromWishlist(element) {
  const li = element.closest('[data-product-id]');
  const wishlistProductId = li?.getAttribute('data-product-id');
  return wishlistProductId;
}

async function removeFromWishlist(wishlistItemId) {
  if (!wishlistItemId) {
    return;
  }
  try {
    const response = await fetch(`/api/wishlist/${wishlistItemId}`, {
      method: 'DELETE',
      credentials: 'include',
    });

    if (response.ok) {
      const listItem = document.querySelector(
        `[data-witem-id="${wishlistItemId}"]`
      );
      if (listItem) {
        listItem.classList.add('d-none');
      }
      alert('Product removed from wishlist!');
    } else {
      const error = await response.text();
      alert('Failed to remove product from wishlist: ' + error);
    }
  } catch (error) {
    console.error('Error removing from wishlist:', error);
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

async function addAllToCart() {
  try {
    const response = await fetch('/api/wishlist/addAllToCart', {
      method: 'POST',
      credentials: 'include',
    });

    if (response.ok) {
      alert('All items added to cart!');
    } else {
      const error = await response.text();
      alert('Failed to add items to cart: ' + error);
    }
  } catch (error) {
    console.error('Error adding all items to cart:', error);
  }
}

async function clearWishlist() {
  if (!confirm('Are you sure you want to clear your wishlist?')) {
    return;
  }
  try {
    const response = await fetch('/api/wishlist/clear', {
      method: 'DELETE',
      credentials: 'include',
    });

    if (response.ok) {
      alert('Wishlist cleared!');
      location.reload();
    } else {
      const error = await response.text();
      alert('Failed to clear wishlist: ' + error);
    }
  } catch (error) {
    console.error('Error clearing wishlist:', error);
  }
}
