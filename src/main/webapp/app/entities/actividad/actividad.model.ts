import { BaseEntity } from './../../shared';

export const enum DIFICULTAD {
    'FACIL',
    'INTERMEDIO',
    'DIFICIL'
}

export class Actividad implements BaseEntity {
    constructor(
        public id?: number,
        public enunciado?: string,
        public dificultad?: DIFICULTAD,
        public esQuiz?: boolean,
        public planeta?: BaseEntity,
        public profesor?: BaseEntity,
        public respuestas?: BaseEntity[],
        public ayudas?: BaseEntity[],
    ) {
        this.esQuiz = false;
    }
}
