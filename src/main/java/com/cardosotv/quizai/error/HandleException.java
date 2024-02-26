package com.cardosotv.quizai.error;


public class HandleException {

    public static RuntimeException handleException(Throwable t, Object object, String entityName) {
        return switch (t.getClass().getSimpleName()) {
                case "NotFoundException" -> 
                    new NotFoundException(entityName, object);
                case "BadRequestException" -> 
                    new BadRequestException("Bad Request!", object);
                case "UnauthorizedException" -> 
                    new UnauthorizedException();
                default -> 
                    new ServerError(t.getClass().getName(), object);
            };
    }
}
