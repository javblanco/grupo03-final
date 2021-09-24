describe('Creación y listado de productos', () => {
    let numeroRegistros: number;
 
   it('Visitar la vista del listado y comprobar los tipos cargados por defecto', () => {
    cy.visit("/");
    cy.contains('Producto').click();
    cy.contains('Ver listado').click();
 
     cy.get('table#tabla-producto tbody tr').should('have.length.at.least', 2);
     cy.get('table#tabla-producto tbody tr').contains('Noespresso');
 
     cy.get('table#tabla-producto tbody tr').then($elements => {
       numeroRegistros = $elements.length;
     });
   });
 
   it('Va a la página de creación y da de alta un nuevo registro', () => {
     cy.get('#crear-producto').click();
 
     cy.url().should('not.contain', 'list');
 
     cy.get('input#producto-nombre').type('Producto 1');
     
     cy.get('select#producto-tipo').select('Cafetera');

     cy.get('input#producto-marca').type('Marca 1');

     cy.get('input#producto-modelo').type('Modelo 1');

     cy.get('input#producto-stock-almacen').type('10');

     cy.get('input#producto-stock-tienda').type('10');
 
     cy.get('button#producto-guardar').click();
 
     cy.get('#feedback').contains('creado');
   });
 
   it('Vuelve al listado y comprueba que haya un registro más', () => {
     cy.get('button#producto-volver').click();
 
     cy.get('button#modal-boton-volver').click();

     cy.url().should('contain', 'list');
 
     cy.get('table#tabla-producto tbody tr').should('have.length', numeroRegistros + 1);
     cy.get('table#tabla-producto tbody tr').contains('Producto 1');
     cy.get('table#tabla-producto tbody tr').contains('Marca 1');
     cy.get('table#tabla-producto tbody tr').contains('Modelo 1');
 
   });
 })

 describe('Modificación del producto.', () => {
   it('Seleccionamos el producto de nombre "Producto 1" y lo modificamos', () => {
    cy.get('table#tabla-producto tbody tr').contains('Producto 1').parent().find(".producto-modificar").click();
    cy.url().should('not.contain', 'list');

    cy.get('input#producto-nombre').clear().type('Producto 1 modificado');

    cy.get('button#producto-guardar').click();

    cy.get('button#modal-boton-guardar').click();

    cy.get('#feedback').contains('modificado');
   });

   it('Volvemos al listado y comprobamos el cambio', () => {
     cy.visit('/producto/listar');

     cy.get('table#tabla-producto tbody tr').contains('Producto 1 modificado');

   })
 });
 