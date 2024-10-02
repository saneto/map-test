-- Update the status of duplicate resources to 1, only when the associated bulk load has a status of 3
UPDATE resources
SET status = 1
WHERE id IN (
    SELECT r.id
    FROM resources r
    JOIN (
        SELECT resource_name, bulkload_id
        FROM resources
        GROUP BY resource_name, bulkload_id
        HAVING COUNT(*) > 1
    ) AS duplicates ON r.resource_name = duplicates.resource_name
        AND r.bulkload_id = duplicates.bulkload_id
    JOIN bulkloads b ON r.bulkload_id = b.id  -- Assuming you have a bulkloads table
    WHERE b.status = 3  -- Filter to ensure bulkload status is 3
);