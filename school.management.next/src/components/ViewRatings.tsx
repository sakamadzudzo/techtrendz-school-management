import { ActivityPoint, Rating } from "@/utils/types";
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  SortDescriptor,
  Button,
  NumberInput,
} from "@heroui/react";
import MyTable, { Column } from "./MyTable";
import React from "react";
import useToastError from "@/hooks/toasts/ToastError";
import { RootState } from "@/store/store";
import { useSelector } from "react-redux";
import { useSaveAllRatings } from "@/hooks/useRatings";

type ViewRatingsProps = {
  ratings?: Rating[];
  isOpen: boolean;
  activityPointId: number;
  activityName: string;
  studentName: string;
  onOpenChange: (open: boolean) => void;
  incrementLoading: () => void;
  decrementLoading: () => void;
  reloadTrigger: () => void;
};

interface CustomRating extends Rating {
  activityName: string;
}

export const ViewRatings = ({
  ratings,
  isOpen,
  activityPointId,
  activityName,
  studentName,
  onOpenChange,
  incrementLoading,
  decrementLoading,
  reloadTrigger,
}: ViewRatingsProps) => {
  const [error, setError] = React.useState<string | null>(null);
  const [statusCode, setStatusCode] = React.useState(0);
  const token = useSelector((state: RootState) => state.auth.token);

  const page = 0,
    pages = 1,
    total = ratings?.length || 0,
    size = ratings?.length || 0,
    offset = 0,
    first = true,
    last = true,
    isLoading = false;

  const [filterValue, setFilterValue] = React.useState<string>("");
  const [sortDescriptor, setSortDescriptor] = React.useState<SortDescriptor>({
    column: "ratingString",
    direction: "descending",
  });
  const initialRating: Rating = {
    activityPoint: { id: activityPointId } as ActivityPoint,
  } as Rating;
  const [localRatings, setLocalRatings] = React.useState<Rating[]>(
    ratings || []
  );
  const [editingRatingId, setEditingRatingId] = React.useState<number | null>(
    null
  );
  const [inputValue, setInputValue] = React.useState<number | undefined>(
    undefined
  );
  const [originalRatingsTrack, setOriginalRatingsTrack] = React.useState<
    { ratingId: number; originalRating: number | undefined }[]
  >([]);
  const [tempIdCounter, setTempIdCounter] = React.useState<number>(0);
  const { saveAllRatings } = useSaveAllRatings();

  const hasChanges = React.useMemo(() => {
    return originalRatingsTrack?.length !== 0;
  }, [originalRatingsTrack]);

  React.useEffect(() => {
    if (isOpen) {
      setLocalRatings(ratings || []);
      setOriginalRatingsTrack([]);
    }
  }, [isOpen, ratings]);

  const columns = React.useMemo<Column[]>(() => {
    return [
      {
        label: "Rating",
        key: "ratingString",
        allowsSorting: true,
      },
      {
        label: "Actions",
        key: "actions",
        allowsSorting: false,
      },
    ];
  }, []);

  const saveRatings = async () => {
    if (!hasChanges) {
      useToastError({ error: "There are no changes to save" });
      return;
    }

    const changedIds: number[] = originalRatingsTrack.map((item) =>
      Number(item.ratingId)
    );
    const changes = localRatings.filter((item) =>
      changedIds.includes(Number(item.id))
    );

    // Convert temporary IDs to proper format for saving
    const ratingsToSave = changes.map((rating) => ({
      id: rating.id > 0 ? rating.id : undefined, // Remove temp IDs
      activityPoint: { id: activityPointId },
      rating: rating.rating,
    }));

    await saveAllRatings(
      ratingsToSave,
      incrementLoading,
      decrementLoading,
      () => {
        reloadTrigger();
        onOpenChange(false);
      },
      (error, statusCode) => {
        useToastError({ error, statusCode });
      } // onError callback
    );
  };

  const ratingInput = (
    ratingId: number | undefined,
    value: number | undefined,
    onChange: (value: number) => void
  ) => {
    const handleAccept = () => {
      const numValue: number = !value || isNaN(value) ? 0 : value;
      if (ratingId) {
        const currentRating = localRatings?.find(
          (r) => Number(r.id) === Number(ratingId)
        );
        const currentRatingValue = currentRating
          ? currentRating.rating
          : undefined;

        // Track original rating if not already tracked
        setOriginalRatingsTrack(
          (
            prev: { ratingId: number; originalRating: number | undefined }[]
          ) => {
            const existing = prev.find(
              (p: { ratingId: number; originalRating: number | undefined }) =>
                p.ratingId === ratingId
            );
            if (!existing) {
              return [
                ...prev,
                { ratingId, originalRating: currentRatingValue },
              ];
            }
            return prev;
          }
        );

        // Update existing rating
        setLocalRatings((prev) =>
          prev.map((r) => (r.id === ratingId ? { ...r, rating: numValue } : r))
        );
      }
      setEditingRatingId(null);
    };

    const handleClose = () => {
      setEditingRatingId(null);
    };

    return (
      <>
        <div className="flex space-x-2 items-center w-fit">
          <NumberInput
            name="rating-input"
            label="Rating"
            className="w-32 rounded-lg"
            size="sm"
            variant="bordered"
            placeholder="Enter rating"
            value={Number(value)}
            onValueChange={onChange}
            autoFocus={true}
          />
          <Button onPress={handleAccept} color="primary">
            Accept
          </Button>
          <Button onPress={handleClose} color="danger">
            Close
          </Button>
        </div>
      </>
    );
  };

  const rows = React.useMemo(() => {
    return localRatings
      ?.map((rating: Rating) => {
        let ratingStringValue: string | React.ReactNode;

        if (Number(editingRatingId) === Number(rating.id)) {
          ratingStringValue = ratingInput(rating.id, inputValue, setInputValue);
        } else {
          ratingStringValue = rating.rating.toString();
        }

        return {
          key: rating.id || `${activityPointId}-${rating.rating}`,
          ...rating,
          activityName: activityName,
          ratingString: ratingStringValue,
          actions: [
            {
              label: "Edit Rating",
              function: () => {
                editRating(rating.id);
              },
            },
          ],
        };
      })
      ?.filter((rating) =>
        rating.activityName?.toLowerCase().includes(filterValue.toLowerCase())
      )
      .sort((a: CustomRating, b: CustomRating) => {
        const col: keyof CustomRating =
          sortDescriptor.column as keyof CustomRating;
        if (a[col]! < b[col]!)
          return sortDescriptor.direction === "ascending" ? -1 : 1;
        if (a[col]! > b[col]!)
          return sortDescriptor.direction === "ascending" ? 1 : -1;
        return 0;
      });
  }, [
    localRatings,
    activityPointId,
    activityName,
    filterValue,
    sortDescriptor,
    editingRatingId,
    inputValue,
  ]);

  const editRating = (ratingId: number) => {
    const currentRating = localRatings?.find(
      (r) => Number(r.id) === Number(ratingId)
    );
    setInputValue(currentRating ? currentRating.rating : undefined);
    setEditingRatingId(ratingId);
  };

  const addFunction = () => {
    const tempId = -tempIdCounter - 1; // Use negative IDs for temporary entries
    const newRating: Rating = {
      id: tempId,
      activityPoint: { id: activityPointId } as ActivityPoint,
      rating: 0,
    } as Rating;

    setLocalRatings((prev) => [...prev, newRating]);
    setTempIdCounter((prev) => prev + 1);
    setEditingRatingId(tempId);
    setInputValue(0);
  };

  const handleReset = () => {
    // Reset to original ratings and clear tracking
    setLocalRatings(ratings || []);
    setOriginalRatingsTrack([]);
    setEditingRatingId(null);
    setInputValue(undefined);
    setTempIdCounter(0);
  };

  return (
    <Modal
      isOpen={isOpen}
      onOpenChange={onOpenChange}
      hideCloseButton={false}
      backdrop="blur"
      radius="md"
      shadow="md"
      size="4xl"
      classNames={{
        wrapper: "py-2 overflow-hidden",
      }}
      className="text-foreground"
    >
      <ModalContent className="h-full">
        <ModalHeader className="text-xl font-bold flex justify-between">
          {`View/Edit ratings for ${studentName} in ${activityName}`}
          <div className="flex space-x-2 pr-8">
            {hasChanges ? (
              <>
                <Button
                  type="submit"
                  color="primary"
                  className="w-fit"
                  onPress={saveRatings}
                >
                  Save Ratings
                </Button>
                <Button
                  type="button"
                  color="default"
                  className="w-fit"
                  onPress={handleReset}
                >
                  Reset
                </Button>
              </>
            ) : null}
          </div>
        </ModalHeader>
        <ModalBody className="grow mt-4 overflow-hidden">
          <MyTable
            key={`ratings-table`}
            isHeaderSticky={true}
            columns={columns}
            rows={rows}
            setPage={() => {}}
            page={page}
            pages={pages}
            sortDescriptor={sortDescriptor}
            setSortDescriptor={setSortDescriptor}
            filterValue={filterValue}
            setFilterValue={setFilterValue}
            // searchBox={true}
            // search={() => setFilterValue(filterValue)}
            isLoading={isLoading}
            total={total}
            size={size}
            setSize={() => {}}
            offset={offset}
            first={first}
            last={last}
            nextPage={() => {}}
            prevPage={() => {}}
            addFunction={addFunction}
            onRowAction={(row) => {
              editRating(row);
            }}
          />
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};
