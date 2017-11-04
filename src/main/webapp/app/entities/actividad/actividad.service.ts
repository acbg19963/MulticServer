import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Actividad } from './actividad.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ActividadService {

    private resourceUrl = SERVER_API_URL + 'api/actividads';

    constructor(private http: Http) { }

    create(actividad: Actividad): Observable<Actividad> {
        const copy = this.convert(actividad);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(actividad: Actividad): Observable<Actividad> {
        const copy = this.convert(actividad);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Actividad> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Actividad.
     */
    private convertItemFromServer(json: any): Actividad {
        const entity: Actividad = Object.assign(new Actividad(), json);
        return entity;
    }

    /**
     * Convert a Actividad to a JSON which can be sent to the server.
     */
    private convert(actividad: Actividad): Actividad {
        const copy: Actividad = Object.assign({}, actividad);
        return copy;
    }
}
