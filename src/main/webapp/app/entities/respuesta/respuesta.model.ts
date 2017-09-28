import { BaseEntity } from './../../shared';

export class Respuesta implements BaseEntity {
    constructor(
        public id?: number,
        public enunciado?: string,
        public acertado?: boolean,
        public actividad?: BaseEntity,
    ) {
        this.acertado = false;
    }
}
