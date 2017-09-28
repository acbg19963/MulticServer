import { BaseEntity } from './../../shared';

export class Avatar implements BaseEntity {
    constructor(
        public id?: number,
        public monedas?: number,
        public nombre?: string,
        public cabello?: string,
        public ropa?: string,
        public gafas?: string,
        public universo?: BaseEntity,
        public estudiante?: BaseEntity,
    ) {
    }
}
