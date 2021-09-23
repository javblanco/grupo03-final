let numeroRegistros: number;
let urlModificacion: string;

describe('Creación y listado del tipo de productos', () => {


  it('Visitar la vista del listado y comprobar los tipos cargados por defecto', () => {
    cy.visit("/");
    cy.contains('Tipo de producto').click();
    cy.contains('Ver listado').click();


    cy.url().should('contain', '/tipo-producto/listar');

    cy.get('table#tabla-tipo-producto tbody tr').should('have.length.at.least', 2);
    cy.get('table#tabla-tipo-producto tbody tr').contains('Taza');

    cy.get('table#tabla-tipo-producto tbody tr').then($elements => {
      numeroRegistros = $elements.length;
    });
  });

  it('Va a la página de creación y da de alta un nuevo registro', () => {
    cy.get('#crear-tipo').click();

    cy.url().should('not.contain', 'list');

    cy.get('input#tipo-nombre').type('Tipo 1');
    
    cy.get('textarea#tipo-descripcion').type('Descripción tipo 1');

    cy.get('button#tipo-guardar').click();

    cy.get('#feedback').contains('creado');
  });

  it('Vuelve al listado y comprueba que haya un registro más', () => {
    cy.get('button#tipo-volver').click();

    cy.get('button#modal-boton-volver').click();

    cy.url().should('contain', 'list');

    cy.get('table#tabla-tipo-producto tbody tr').should('have.length', numeroRegistros + 1);
    cy.get('table#tabla-tipo-producto tbody tr').contains('Tipo 1');

  });
});

describe('Modificación del tipo y cambio de estado.', () => {
  it('Seleccionamos el tipo de producto de nombre "Tipo 1" y lo modificamos a "Vaso"', () => {
    cy.get('table#tabla-tipo-producto tbody tr').contains('Tipo 1').parent().find(".tipo-modificar").click();
    cy.url().should('not.contain', 'list');

    cy.url().then(url => urlModificacion = url);

    cy.get('input#tipo-nombre').clear().type('Tipo 1 modificado');
    cy.get('button#tipo-guardar').click();

    cy.get('button#modal-boton-guardar').click();

    cy.get('#feedback').contains('modificado');

  });

  it('Volvemos a la lista y comprobamos el cambio', () =>  {
    cy.get('button#tipo-volver').click();

    cy.get('button#modal-boton-volver').click();

    cy.url().should('contain', 'list');

    cy.get('table#tabla-tipo-producto tbody tr').contains('Tipo 1 modificado');
  });

  it('Vamos al elemento y cambiamos el estado a baja', () => {
    cy.get('table#tabla-tipo-producto tbody tr').contains('Tipo 1 modificado').parent().find(".tipo-modificar").click();

    cy.get('button#tipo-activo').click();

    cy.get('button#modal-boton-baja').click();
    cy.get('#feedback').contains('baja');
  });

  it('Vamos al listado y comprobamos que ahora se lista un elemento menos y que el elemento "Tipo 1 modificado" no aparece', () => {
    cy.visit("/tipo-producto/listar");

    cy.get('table#tabla-tipo-producto tbody tr').should('have.length', numeroRegistros);
    cy.get('table#tabla-tipo-producto tbody tr').should('not.contain', 'Tipo 1 modificado');
  });

  it('Volvemos a la página de modificación y cambiamos el estado a alta', () => {
    cy.then(() => {
      cy.visit(urlModificacion);
      cy.get('button#tipo-activo').click();

    cy.get('button#modal-boton-alta').click();
    cy.get('#feedback').contains('alta');
    });
    
  });

  it('Vamos al listado y comprobamos que ahora se lista un elemento más y que el elemento "Tipo 1 modificado" aparece', () => {
    cy.visit("/tipo-producto/listar");

    cy.get('table#tabla-tipo-producto tbody tr').should('have.length', numeroRegistros +1);
    cy.get('table#tabla-tipo-producto tbody tr').contains('Tipo 1 modificado');
  });

});

