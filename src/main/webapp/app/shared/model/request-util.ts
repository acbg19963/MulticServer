import { URLSearchParams, BaseRequestOptions } from '@angular/http';

export const createRequestOption = (req?: any): BaseRequestOptions => {
    console.log("asdsadsa")
    const options: BaseRequestOptions = new BaseRequestOptions();
    const params: URLSearchParams = new URLSearchParams();
    if (req) {
       
        params.set('page', req.page);
        params.set('size', "9999");
        if (req.sort) {
            params.paramsMap.set('sort', req.sort);
        }
        params.set('query', req.query);

        options.params = params;
    }
    
    params.set('size', "9999");
    options.params = params;
    return options;
};
