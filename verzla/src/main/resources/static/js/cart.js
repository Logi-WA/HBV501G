document.addEventListener('DOMContentLoaded', async () => {
  // Check if the user is logged in
  const isLoggedIn = await checkUserLoginStatus();

  // If logged in, attach event listeners to buttons
  if (isLoggedIn) {
    attachCartEventListeners();
  }

  // Reattach listeners when navigating back (e.g., from wishlist or cart pages)
  window.addEventListener('popstate', () => {
    attachCartEventListeners();
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

function attachCartEventListeners() {
  // Use event delegation
  const container = document.getElementById('cartPage');
  if (!container) {
    return;
  }

  // Add event listener to the container, listening for button clicks
  container.addEventListener('click', async (event) => {
    // Prevent scrolling
    event.preventDefault();

    const target = event.target;

    if (target.closest('.btn')?.classList.contains('removeFromCart')) {
      const cartItemId = getCartItemFromList(target);
      await removeFromCart(cartItemId);
    } else if (
      target.closest('.btn')?.classList.contains('incrementQuantity')
    ) {
      const cartItemId = getCartItemFromList(target);
      await incrementCartItem(cartItemId);
    } else if (
      target.closest('.btn')?.classList.contains('decrementQuantity')
    ) {
      const cartItemId = getCartItemFromList(target);
      await decrementCartItem(cartItemId);
    }
  });
}

// Helper function to extract cart item Id from cart list
function getCartItemFromList(element) {
  const li = element.closest('[data-citem-id]');
  const cartItemId = li?.getAttribute('data-citem-id');
  return cartItemId;
}

async function removeFromCart(cartItemId) {
  if (!cartItemId) {
    return;
  }
  try {
    const response = await fetch(`/api/cart/${cartItemId}`, {
      method: 'DELETE',
      credentials: 'include',
    });

    if (response.ok) {
      const listItem = document.querySelector(
        `[data-citem-id="${cartItemId}"]`
      );
      if (listItem) {
        listItem.classList.add('d-none');
      }
      alert('Product removed from cart!');
    } else {
      const error = await response.text();
      alert('Failed to remove product from cart: ' + error);
    }
  } catch (error) {
    console.error('Error removing from cart:', error);
  }
}

function updateDecrementButtonState(cartItemId, quantity) {
  const decrementButton = document.querySelector(
    `[data-citem-id="${cartItemId}"] .decrementQuantity`
  );
  if (quantity <= 1) {
    decrementButton.disabled = true;
  } else {
    decrementButton.disabled = false;
  }
}

async function incrementCartItem(cartItemId) {
  if (!cartItemId) {
    return;
  }

  // Get the current quantity
  const quantityInput = document.getElementById(`quantity-input-${cartItemId}`);
  let currentQuantity = parseInt(quantityInput.value, 10);

  // Increment the quantity
  const newQuantity = currentQuantity + 1;

  // Update the quantity on the server
  await updateCartItemQuantity(cartItemId, newQuantity);

  // Update the quantity on the client
  quantityInput.value = newQuantity;
  updateDecrementButtonState(cartItemId, newQuantity);
}

async function decrementCartItem(cartItemId) {
  if (!cartItemId) {
    return;
  }

  // Get the current quantity
  const quantityInput = document.getElementById(`quantity-input-${cartItemId}`);
  let currentQuantity = parseInt(quantityInput.value, 10);

  // Decrement the quantity, ensuring it doesn't go below 1
  const newQuantity = Math.max(1, currentQuantity - 1);

  if (newQuantity === currentQuantity) {
    // Quantity is already at minimum, do nothing
    return;
  }

  // Update the cart item quantity on the server
  await updateCartItemQuantity(cartItemId, newQuantity);

  // Update the quantity in the UI
  quantityInput.value = newQuantity;
  updateDecrementButtonState(cartItemId, newQuantity);
}

async function updateCartItemQuantity(cartItemId, quantity) {
  try {
    const response = await fetch(`/api/cart/${cartItemId}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ quantity }),
      credentials: 'include',
    });

    if (!response.ok) {
      const error = await response.text();
      alert('Failed to update cart item quantity: ' + error);
    }
  } catch (error) {
    console.error('Error updating cart item quantity:', error);
  }
}
