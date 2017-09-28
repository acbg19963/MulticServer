import { BaseEntity, User } from './../../shared';

export class Profesor implements BaseEntity {
    constructor(
        public id?: number,
        public materia?: string,
        public usuario?: User,
        public actividades?: BaseEntity[],
        public cursos?: BaseEntity[],
    ) {
    }
}
