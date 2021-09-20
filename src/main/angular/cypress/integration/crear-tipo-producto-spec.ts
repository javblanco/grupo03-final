describe('Creación y listado del tipo de productos', () => {
  it('Visitar la vista del listado y comprobar los tipos cargados por defecto', () => {
    cy.visit('/tipo-producto/listar');

    cy.get('table#tabla-tipo-producto tbody tr').should('have.length.at.least', 4);
    cy.get('table#tabla-tipo-producto tbody tr').contains('Libreta');
  })
})
