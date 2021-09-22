describe('Creación y listado del tipo de productos', () => {
   let numeroRegistros: number;

  it('Visitar la vista del listado y comprobar los tipos cargados por defecto', () => {
    cy.visit('/tipo-producto/listar');

    cy.get('table#tabla-tipo-producto tbody tr').should('have.length.at.least', 4);
    cy.get('table#tabla-tipo-producto tbody tr').contains('Libreta');

    cy.get('table#tabla-tipo-producto tbody tr').then($elements => {
      numeroRegistros = $elements.length;
    });
  });

  it('Va a la página de creación y da de alta un nuevo registro', () => {
    cy.get('#crear-tipo').click();

    cy.url().should('not.contain', 'list');

    cy.get('input#tipo-nombre').type('Producto 1');
    
    cy.get('textarea#tipo-descripcion').type('Descripción producto 1');

    cy.get('button#tipo-guardar').click();

    cy.get('#feedback').contains('creado');
  });

  it('Vuelve al listado y comprueba que haya un registro más', () => {
    cy.get('button#tipo-volver').click();

    cy.url().should('contain', 'list');

    cy.get('table#tabla-tipo-producto tbody tr').should('have.length', numeroRegistros + 1);
    cy.get('table#tabla-tipo-producto tbody tr').contains('Producto 1');

  });
})
