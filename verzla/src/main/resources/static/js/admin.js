// admin.js

document.addEventListener('DOMContentLoaded', () => {
  // Initialize the modal
  const modalElement = document.getElementById('editModal');
  const modal = new bootstrap.Modal(modalElement);

  // Event delegation for edit buttons
  document.addEventListener('click', (event) => {
    const target = event.target;

    // Check if the clicked element is the edit button or contains it
    if (
      target.classList.contains('edit-product-button') ||
      target.closest('.edit-product-button')
    ) {
      const button = target.closest('.edit-product-button');
      const productId = button.getAttribute('data-product-id');
      const productName = button.getAttribute('data-product-name');
      const productDescription = button.getAttribute('data-product-description');

      // Populate modal fields
      document.getElementById('editProductId').value = productId;
      document.getElementById('productName').value = productName || '';
      document.getElementById('productDescription').value = productDescription || '';

      modal.show();
    }
  });

  // Save Changes button event listener
  document.getElementById('saveChanges').addEventListener('click', async () => {
    const productId = document.getElementById('editProductId').value;
    const newName = document.getElementById('productName').value.trim();
    const newDescription = document.getElementById('productDescription').value.trim();

    if (!productId || !newName || !newDescription) {
      alert("Product ID, name, or description is missing.");
      return;
    }

    // Update product
    await updateProduct(productId, newName, newDescription);

    modal.hide();
    location.reload(); // Reload page to reflect changes
  });
});

// Function to update product name and description
async function updateProduct(productId, newName, newDescription) {
  const endpoint = `/api/products/${productId}`;

  try {
    const response = await fetch(endpoint, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        name: newName,
        description: newDescription
      })
    });

    if (!response.ok) {
      const error = await response.text();
      alert(`Failed to update product: ${error}`);
    }
  } catch (error) {
    console.error(`Error updating product:`, error);
    alert(`An error occurred while updating the product: ${error.message}`);
  }
}
