import { BaseEntity } from './../../shared';

export const enum COLOR {
    'AZUL',
    'MORADO',
    'ROJO',
    'AMARILLO'
}

export class Avatar implements BaseEntity {
    constructor(
        public id?: number,
        public monedas?: number,
        public nombre?: string,
        public cabello?: COLOR,
        public ropa?: COLOR,
        public universo?: BaseEntity,
        public estudiante?: BaseEntity,
    ) {
    }
}
