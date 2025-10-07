import pages from "@/pages";
import { ActivityPoint, Rating } from "@/utils/types";
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  SortDescriptor,
  Button,
  NumberInput,
  ModalFooter,
} from "@heroui/react";
import MyTable, { Column } from "./MyTable";
import React from "react";
import useToastError from "@/hooks/toasts/ToastError";
import { RootState } from "@/store/store";
import { useSelector } from "react-redux";

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
    column: "name",
    direction: "ascending",
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

  React.useEffect(() => {
    if (isOpen) {
      setLocalRatings(ratings || []);
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

  const addRating = async (activityId: number, rating: number) => {
    setError(null);
    setStatusCode(0);
    incrementLoading();

    const newRating: Rating = {
      activityPoint: { id: activityId } as ActivityPoint,
      rating: rating,
    } as Rating;

    try {
      const res = await fetch("/api/rating?action=save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(newRating),
      });
      const data = await res.json();
      if (!res.ok) {
        const err = data.error || "Adding rating failed";
        const code = res.status || 500;
        setError(err);
        setStatusCode(code);
        useToastError({ error: err, statusCode: code });
        return;
      } else {
        reloadTrigger();
      }
    } catch (err: any) {
      setError(err.response?.data?.message || "Network/Server error");
      setStatusCode(err.status || 500);
    } finally {
      decrementLoading();
    }
  };

  const ratingInput = (
    ratingId: number | undefined,
    value: number | undefined,
    onChange: (value: number) => void
  ) => {
    const handleAccept = () => {
      const numValue: number = !value || isNaN(value) ? 0 : value;
      if (ratingId) {
        // Update existing rating
        setLocalRatings((prev) =>
          prev.map((r) => (r.id === ratingId ? { ...r, rating: numValue } : r))
        );
      } else {
        // Add new rating
        addRating(activityPointId, numValue);
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
        <ModalHeader className="text-xl font-bold">
          {`View/Edit ratings for ${studentName} in ${activityName}`}
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
            addFunction={() => {}}
            onRowAction={(row) => {
              editRating(row);
            }}
          />
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};
