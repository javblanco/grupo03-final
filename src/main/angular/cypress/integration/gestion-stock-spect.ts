describe('Pasar stock a la tienda', () => {
    let cantidadAlmacen: number;
    let cantidadTienda: number;

    it(`Se navega hasta la zona de pasar a tienda y se selecciona el producto
         'Noespresso'`, () => {
        cy.visit("/");
        cy.get('.container').contains('Gestión de stock').click();
        cy.contains('Pasar a tienda').click();

        cy.url().should('contain', '/transferir');

        cy.get('#select-producto').select('Noespresso');

        cy.get('h3').contains('Noespresso');
    });

    it(`Se coloca la cantidad de 0 y se da al botón de Traspasar. Se observa que sale un error.`, () => {
        cy.get('input#cantidad-traspasar').clear().type('0');

        cy.get('button#transferir-guardar').click();

        cy.get('div').contains('Introduzca una cantidad.');
    });

    it(`Se coloca la cantidad de negativa y se da al botón de Traspasar. Se observa que sale un error.`, () => {
        cy.get('input#cantidad-traspasar').clear().type('-10');

        cy.get('button#transferir-guardar').click();

        cy.get('div').contains('No se admiten números negativos');
    });

    it(`Se coloca la cantidad de superior a las unidades en almacén y se da al botón de Traspasar. Se observa que sale un error.`, () => {
        cy.get('input#cantidad-almacen').invoke('val').then(value => cantidadAlmacen = Number(value));
        cy.get('input#cantidad-tienda').invoke('val').then(value => cantidadTienda = Number(value));

        cy.then(() => {
            cy.get('input#cantidad-traspasar').clear().type(String(cantidadAlmacen + 10));

            cy.get('button#transferir-guardar').click();


            cy.get('div').contains('No hay unidades suficientes en el almacén');
        });
    });

    it(`Se coloca la cantidad. y se comprueba que las cantidades en almacén y tienda han cambiando`, () => {

        cy.get('input#cantidad-traspasar').clear().type('10');
        cy.get('button#transferir-guardar').click();
        cy.get('button#modal-boton-transferir').click();

        cy.then(() => {
            cy.get('input#cantidad-almacen').should('have.value', cantidadAlmacen - 10);
            cy.get('input#cantidad-tienda').should('have.value', cantidadTienda + 10);
        });




    });
});

describe('Devolver al almacén', () => {
    let cantidadAlmacen: number;
    let cantidadTienda: number;

    it(`Se navega hasta la zona de devolver al almacén y se selecciona el producto
     'Noespresso'`, () => {
        cy.visit("/");
        cy.get('.container').contains('Gestión de stock').click();
        cy.contains('Devolver al almacén').click();

        cy.url().should('contains', '/devolver');

        cy.get('#select-producto').select('Noespresso');

        cy.get('h3').contains('Noespresso');
    });

    it(`Se coloca la cantidad de 0 y se da al botón de Traspasar. Se observa que sale un error.`, () => {
        cy.get('input#cantidad-traspasar').clear().type('0');

        cy.get('button#transferir-guardar').click();

        cy.get('div').contains('Introduzca una cantidad.');
    });

    it(`Se coloca la cantidad de negativa y se da al botón de Traspasar. Se observa que sale un error.`, () => {
        cy.get('input#cantidad-traspasar').clear().type('-10');

        cy.get('button#transferir-guardar').click();

        cy.get('div').contains('No se admiten números negativos');
    });

    it(`Se coloca la cantidad de superior a las unidades en tienda y se da al botón de Traspasar. Se observa que sale un error.`, () => {
        cy.get('input#cantidad-almacen').invoke('val').then(value => cantidadAlmacen = Number(value));
        cy.get('input#cantidad-tienda').invoke('val').then(value => cantidadTienda = Number(value));

        cy.then(() => {
            cy.get('input#cantidad-traspasar').clear().type(String(cantidadTienda + 10));

            cy.get('button#transferir-guardar').click();


            cy.get('div').contains('No hay unidades suficientes en la tienda');
        });
    });

    it(`Se coloca la cantidad. y se comprueba que las cantidades en almacén y tienda han cambiando`, () => {

        cy.get('input#cantidad-traspasar').clear().type('10');
        cy.get('button#transferir-guardar').click();
        cy.get('button#modal-boton-devolver').click();

        cy.then(() => {
            cy.get('input#cantidad-almacen').should('have.value', cantidadAlmacen + 10);
            cy.get('input#cantidad-tienda').should('have.value', cantidadTienda - 10);
        });
    });
});

describe('Reponer almacén', () => {
    let cantidadAlmacen: number;
    let cantidadTienda: number;

    it(`Se navega hasta la zona de reponer almacén y se selecciona el producto
         'Noespresso'`, () => {
        cy.visit("/");
        cy.get('.container').contains('Gestión de stock').click();
        cy.contains('Reponer almacén').click();

        cy.url().should('contain', '/reponer');

        cy.get('#select-producto').select('Noespresso');

        cy.get('h3').contains('Noespresso');
    });

    it(`Se coloca la cantidad de 0 y se da al botón de Traspasar. Se observa que sale un error.`, () => {
        cy.get('input#cantidad-traspasar').clear().type('0');

        cy.get('button#transferir-guardar').click();

        cy.get('div').contains('Introduzca una cantidad.');
    });

    it(`Se coloca la cantidad de negativa y se da al botón de Traspasar. Se observa que sale un error.`, () => {
        cy.get('input#cantidad-traspasar').clear().type('-10');

        cy.get('button#transferir-guardar').click();

        cy.get('div').contains('No se admiten números negativos');
    });


    it(`Se coloca la cantidad. y se comprueba que las cantidades en almacén y tienda han cambiando`, () => {
        cy.get('input#cantidad-almacen').invoke('val').then(value => cantidadAlmacen = Number(value));

        cy.get('input#cantidad-traspasar').clear().type('10');
        cy.get('button#transferir-guardar').click();
        cy.get('button#modal-boton-reponer').click();

        cy.then(() => {
            cy.get('input#cantidad-almacen').should('have.value', cantidadAlmacen + 10);
        });




    });
});

