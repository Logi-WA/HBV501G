// datatables.js

window.addEventListener('DOMContentLoaded', (event) => {
  const usersTable = document.getElementById('datatablesUsers');
  if (usersTable) {
    new simpleDatatables.DataTable(usersTable, {
      searchable: true,
      fixedHeight: true,
      perPageSelect: [5, 10, 15, 20],
      perPage: 10, // Default items per page
      sortable: true,
    });
  }

  const productsTable = document.getElementById('datatablesProducts');
  if (productsTable) {
    new simpleDatatables.DataTable(productsTable, {
      searchable: true,
      fixedHeight: true,
      perPageSelect: [5, 10, 15, 20],
      perPage: 10, // Default items per page
      sortable: true,
      columns: [
        { select: 0, sort: "asc" }, // Sort by ID ascending by default
      ],
    });
  }
});
