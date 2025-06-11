package com.api.bookstore.factory;

import com.api.bookstore.models.Book;
import com.github.javafaker.Faker;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class TestDataFactory {

    private final Faker faker;
    private static final Validator validator = buildValidator();

    public TestDataFactory() {
        this.faker = Faker.instance();
    }

    private static Validator buildValidator() {
        try (ValidatorFactory vf = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()) {
            return vf.getValidator();
        }
    }

    public BookFactory books() {
        return new BookFactory(faker, validator);
    }

    public static class BookFactory {

        private final Faker faker;
        private final Validator validator;

        BookFactory(Faker faker, Validator validator) {
            this.faker = faker;
            this.validator = validator;
        }

        private static final Map<String, BiConsumer<Book.BookBuilder, String>> OVERLAY =
                Map.ofEntries(
                        Map.entry("id", (b, v) -> b.id(Integer.parseInt(v))),
                        Map.entry("title", Book.BookBuilder::title),
                        Map.entry("description", Book.BookBuilder::description),
                        Map.entry("pageCount", (b, v) -> b.pageCount(Integer.parseInt(v))),
                        Map.entry("excerpt", Book.BookBuilder::excerpt),
                        Map.entry("publishDate", Book.BookBuilder::publishDate)
                );

        private static void applyOverrides(Book.BookBuilder b, Map<String, String> map) {
            map.forEach((k, v) -> {
                BiConsumer<Book.BookBuilder, String> fn = OVERLAY.get(k);
                if (fn != null) fn.accept(b, v);
            });
        }

        public Book create(Map<String, String> overrides) {
            return create(b -> applyOverrides(b, overrides));
        }

        public Book createWithId(Integer id) {
            return create(b -> b.id(id));
        }

        public Book create() {
            return create(b -> {
            });
        }

        public Book create(Consumer<Book.BookBuilder> customiser) {
            var builder = Book.builder()
                    .id(faker.number().numberBetween(1, 100000))
                    .title(faker.book().title())
                    .description(faker.lorem().paragraph(1))
                    .pageCount(faker.number().numberBetween(1, 100000))
                    .excerpt(faker.lorem().sentence(10))
                    .publishDate(randomRecentDateString());

            customiser.accept(builder);
            Book book = builder.build();
            validate(book);
            return book;
        }

        public Book update(Book original, Map<String, String> overrides) {
            Book.BookBuilder builder = original.toBuilder();
            applyOverrides(builder, overrides);
            return builder.build();
        }

        private void validate(Book book) {
            Set<ConstraintViolation<Book>> violations = validator.validate(book);
            if (!violations.isEmpty()) {
                String msg = violations.stream().map(v -> v.getPropertyPath() + ": " + v.getMessage()).reduce((a, b) -> a + "; " + b).orElse("unknown error");
                throw new IllegalArgumentException("Book validation failed – " + msg);
            }
        }

        private String randomRecentDateString() {
            long daysAgo = faker.number().numberBetween(1, 364);
            Instant date = Instant.now().minus(daysAgo, ChronoUnit.DAYS);
            return date.toString();
        }
    }
}

